from django.db import models


class Activite(models.Model):
    sessionID = models.CharField(max_length=100)
    date = models.CharField(max_length=100)
    heureDebut = models.CharField(max_length=100)
    heureFin = models.CharField(max_length=100)
    nbEleves = models.CharField(max_length=100)


class Eleve(models.Model):
    eleveID = models.CharField(max_length=100)
    nom = models.CharField(max_length=100)
    prenom = models.CharField(max_length=100)


class Dechet(models.Model):
    dechetID = models.CharField(max_length=100)
    activite = models.ForeignKey('Activite', on_delete=models.CASCADE)
    eleve = models.ForeignKey('Eleve', on_delete=models.CASCADE)
    type = models.CharField(max_length=100)
    typePropose = models.CharField(max_length=100)
    reponseEleve = models.CharField(max_length=100, default='none')
    heureRamassage = models.CharField(max_length=100)
