# Generated by Django 3.0.5 on 2020-05-30 21:56

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = []

    operations = [
        migrations.CreateModel(
            name='Activite',
            fields=[
                ('id',
                 models.AutoField(auto_created=True,
                                  primary_key=True,
                                  serialize=False,
                                  verbose_name='ID')),
                ('sessionID', models.CharField(max_length=100)),
                ('date', models.CharField(max_length=100)),
                ('heureDebut', models.CharField(max_length=100)),
                ('heureFin', models.CharField(max_length=100)),
                ('nbEleves', models.CharField(max_length=100)),
            ],
        ),
        migrations.CreateModel(
            name='Eleve',
            fields=[
                ('id',
                 models.AutoField(auto_created=True,
                                  primary_key=True,
                                  serialize=False,
                                  verbose_name='ID')),
                ('eleveID', models.CharField(max_length=100)),
                ('nom', models.CharField(max_length=100)),
                ('prenom', models.CharField(max_length=100)),
            ],
        ),
        migrations.CreateModel(
            name='Dechet',
            fields=[
                ('id',
                 models.AutoField(auto_created=True,
                                  primary_key=True,
                                  serialize=False,
                                  verbose_name='ID')),
                ('dechetID', models.CharField(max_length=100)),
                ('type', models.CharField(max_length=100)),
                ('typePropose', models.CharField(max_length=100)),
                ('reponseEleve',
                 models.CharField(default='none', max_length=100)),
                ('heureRamassage', models.CharField(max_length=100)),
                ('activite',
                 models.ForeignKey(on_delete=django.db.models.deletion.CASCADE,
                                   to='stats.Activite')),
                ('eleve',
                 models.ForeignKey(on_delete=django.db.models.deletion.CASCADE,
                                   to='stats.Eleve')),
            ],
        ),
    ]
