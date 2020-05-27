from django.shortcuts import render, get_object_or_404
from stats.models import Activite, Eleve, Dechet
import mysql.connector

def accueil(request):
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
    conn = mysql.connector.connect(host="localhost",user="pact",password="pactpact", database="pact")
    cursor = conn.cursor()
    cursor.execute("""SELECT MAX(sessionID) FROM GLOBALE """)
    if int(cursor.fetchone()[0]) != int(last_id):
        cursor.execute("""SELECT * FROM GLOBALE WHERE sessionID > %s""", (last_id, ))
        rows = cursor.fetchall()
        for row in rows:
            activite = Activite.objects.create(sessionID=row[0], date=row[1], heureDebut=row[2], heureFin=row[3], nbEleves=row[4])

    eleves = Eleve.objects.all().order_by('eleveID').reverse()
    if len(eleves) == 0:
        last_eleve = 0
    else:
        last_eleve = eleves[0].eleveID
    cursor.execute("""SELECT * FROM ELEVES WHERE eleveID > %s""", (last_eleve,))
    rows = cursor.fetchall()
    for row in rows:
        eleve = Eleve.objects.create(eleveID=row[0], nom=row[1], prenom=row[2])

    conn.close()

def activite(request, sessionID):
    activite = (Activite.objects.filter(sessionID=sessionID))[0]
    mise_a_jour_activite(activite, sessionID)
    dechets = Dechet.objects.filter(activite=activite)
    return render(request, 'stats/activite.html', locals())

def mise_a_jour_activite(activite, sessionID):
    dechets = Dechet.objects.filter(activite=activite)
    conn = mysql.connector.connect(host="localhost",user="pact",password="pactpact", database="pact")
    cursor = conn.cursor()
    if len(dechets) == 0:
        cursor.execute("""SELECT * FROM ELEV_%s""", (sessionID,))
        temp = {}
        rows = cursor.fetchall()
        for row in rows:
            temp[row[0]]=row[1]
        cursor.execute("""SELECT * FROM RAMA_%s""", (sessionID, ))
        row = cursor.fetchall()
        for i in range(1, len(row)):
            eleve = (Eleve.objects.filter(eleveID=temp[row[i,2]]))[0]
            dechet = Dechet.objects.create(dechetID=row[i,0], activite=activite, eleve=eleve, type=row[i,3], typePropose=row[i,4], reponseEleve=row[i,5], heureRamassage=row[i,6])
    conn.close()
