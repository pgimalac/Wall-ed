from django.db import models

class Activite(models.Model):
    sessionID = models.CharField()
    date = models.CharField()
    heureDebut = models.CharField()
    heureFin = models.CharField()
    nbEleves = models.CharField()

class Eleve(models.Model):
    eleveID = models.CharFielf()
    nom = models.CharField()
    prenom = models.CharField()

class Dechet(models.Model):
    dechetID = models.CharField()
    activite = models.ForeignKey('Activite', on_delete=models.CASCADE)
    eleve = models.ForeignKey('Eleve', on_delete=models.CASCADE)
    type = models.CharField()
    typePropose = models.CharField()
    heureRamassage = models.CharField()
