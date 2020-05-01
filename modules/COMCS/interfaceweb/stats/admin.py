from django.contrib import admin
from .models import Eleve, Dechet, Activite

class ActiviteAdmin(admin.ModelAdmin):
    list_display = ('sessionID', 'date', 'heureDebut', 'nbEleves')
    list_filter = ('nbEleves', 'date')
    ordering = ('date', 'heureDebut')
    search_fields = ('nbEleves', 'date')

class EleveAdmin(admin.ModelAdmin):
    list_display = ('nom', 'prenom')
    ordering = ('nom', 'prenom')

class DechetAdmin(admin.ModelAdmin):
    list_display = ('eleve', 'activite', 'type', 'reponseEleve')
    ordering = ('activite', 'heureRamassage')

admin.site.register(Activite, ActiviteAdmin)
admin.site.register(Eleve, EleveAdmin)
admin.site.register(Dechet, DechetAdmin)
