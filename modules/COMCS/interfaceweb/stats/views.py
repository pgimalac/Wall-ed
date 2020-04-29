from django.shortcuts import render, get_object_or_404
from stats.models import Activite, Eleve, Dechet
import mysql.connector

def acceuil(request):
    mise_a_jour_bdd()
    activites = Activite.objects.all().order_by('sessionID').reverse()
    eleves = Eleve.objects.all().order_by('eleveID').reverse()
    return render(request, 'stats/accueil.html', locals())

def mise_a_jour_bdd():
    activites = Activite.objects.all().order_by('sessionID').reverse()
    if len(activites) == 0:
        last_id = 0
    else:
        last_id = activites[0].sessionID
    conn = mysql.connector.connect(host="localhost:3306",user="pact",password="pactpact", database="pact")
    cursor = conn.cursor()
    cursor.execute("""SELECT MAX(sessionID) FROM GLOBALE """)
    if int(cursor.fetchone()) != int(last_id):
        cursor.execute("""SELECT * FROM GLOBALE WHERE sessionID > ?""", (last_id,))
        rows = cursor.fetchall()
        for row in rows:
            activite = Activite.objects.create(sessionID=row[0], date=row[1], heureDebut=row[2], heureFin=row[3], nbEleves=row[4])

    eleves = Eleves.objects.all().order_by('eleveID').reverse()
    if len(eleves) == 0:
        last_eleve = 0
    else:
        last_eleve = eleves[0]
    cursor.execute("""SELECT * FROM ELEVES WHERE eleveID > ?""", (last_eleve,))
    rows = cursor.fetchall()
    for row in rows:
        eleve = Eleve.objects.create(eleveID=row[0], nom=row[1], prenom=row[2])

    conn.close()

def activite(request, sessionID):
    mise_a_jour_activites(sessionID)
    dechets = Dechet.objects.filter(sessionID=sessionID)
    return render(request, locals())

def mise_a_jour_activite(sessionID):
    dechets = Dechet.objects.filter(sessionID=sessionID)
    conn = mysql.connector.connect(host="localhost:3306",user="pact",password="pactpact", database="pact")
    cursor = conn.cursor()
    if len(dechets) == 0:
        cursor.execute("""SELECT * FROM ELEV_?""", (sessionID,))
        temp = {}
        rows = cursor.fetchall()
        for row in rows:
            temp[row[0]]=row[1]
        cursor.execute("""SELECT * FROM RAMA_?""", (sessionID, ))
        rows = cursor.fetchall()
        for row in rows:
            activite = Activite.objects.get_object_or_404(sessionID=sessionID)
            eleve = Eleve.objects.get_object_or_404(eleveID=temp[row[2]])
            dechet = Dechet.objects.create(dechetID=row[0], activite=activite, eleve=eleve, type=row[3], typePropose=row[4], reponseEleve=row[5], heureRamassage=row[6])
    conn.close()
