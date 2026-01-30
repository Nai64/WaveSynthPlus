package com.gallantrealm.easysynth;

public class Translator extends com.gallantrealm.android.Translator {

	@Override
	public String translateSpanish(String text) {
		String translation = text;
		if (text.equals("EasySynth")) {
			translation = "EasySynth";
		}

		// Translations for main panel
		else if (text.equals("Instrument")) {
			translation = "Instrumento";
		} else if (text.equals("Sound")) {
			translation = "Sonar";
		} else if (text.equals("Harmonics")) {
			translation = "Armónicos";
		} else if (text.equals("Filter")) {
			translation = "Filtro";
		} else if (text.equals("Amp")) {
			translation = "Amp";
		} else if (text.equals("Mod")) {
			translation = "Mod";
		} else if (text.equals("Mod2")) {
			translation = "Mod2";
		} else if (text.equals("Echo")) {
			translation = "Echo";
		} else if (text.equals("Arpeggio")) {
			translation = "Arpegio";
		} else if (text.equals("Sequencer")) {
			translation = "Secuenciador";
		} else if (text.equals("Record")) {
			translation = "Grabar";
		} else if (text.equals("Type")) {
			translation = "Clase";
		} else if (text.equals("Shape")) {
			translation = "Forma";
		} else if (text.equals("Amount")) {
			translation = "Cantidad";
		} else if (text.equals("Rate")) {
			translation = "Tasa";
		} else if (text.equals("Sync")) {
			translation = "Sinc";
		} else if (text.equals("Attack")) {
			translation = "Ataque";
		} else if (text.equals("Decay")) {
			translation = "Decaimiento";
		} else if (text.equals("Sustain")) {
			translation = "Sostenido";
		} else if (text.equals("Release")) {
			translation = "Relajación";
		} else if (text.equals("Save")) {
			translation = "Guardar";
		} else if (text.equals("Save Recording")) {
			translation = "Guardar grabación";
		} else if (text.equals("Recording name:")) {
			translation = "Grabación nombre:";
		} else if (text.equals("Saved to")) {
			translation = "Guardado en";
		} else if (text.equals("Failed to save to")) {
			translation = "No se pudo guardar a";
		} else if (text.equals("Send")) {
			translation = "Enviar";
		} else if (text.equals("Delete")) {
			translation = "Borrar";
		} else if (text.equals("Voices")) {
			translation = "Voces";
		} else if (text.equals("Mono")) {
			translation = "Mono";
		} else if (text.equals("Poly")) {
			translation = "Poli";
		} else if (text.equals("Chorus")) {
			translation = "Coro";
		} else if (text.equals("Chorus Width")) {
			translation = "Ancho Coro";
		} else if (text.equals("Portamento")) {
			translation = "Portamento";
		} else if (text.equals("Velocity")) {
			translation = "Velocidad";
		} else if (text.equals("Expression")) {
			translation = "Expresión";
		} else if (text.equals("Octave")) {
			translation = "Octava";
		} else if (text.equals("Hold")) {
			translation = "Mantener";
		} else if (text.equals("Key")) {
			translation = "Clave";
		} else if (text.equals("Resonance")) {
			translation = "Resonancia";
		} else if (text.equals("Range")) {
			translation = "Rango";
		} else if (text.equals("low")) {
			translation = "bajo";
		} else if (text.equals("high")) {
			translation = "alto";
		} else if (text.equals("Volume")) {
			translation = "Volumen";
		} else if (text.equals("Overdrive")) {
			translation = "Overdrive";
		} else if (text.equals("per Voice")) {
			translation = "por Voz";
		} else if (text.equals("Amount")) {
			translation = "Cantidad";
		} else if (text.equals("Delay")) {
			translation = "Delay";
		} else if (text.equals("Feedback")) {
			translation = "Realimentación";
		} else if (text.equals("Flange")) {
			translation = "Flange";
		} else if (text.equals("Rate")) {
			translation = "Tasa";
		} else if (text.equals("Glide")) {
			translation = "Glide";
		} else if (text.equals("Note")) {
			translation = "Nota";
		} else if (text.equals("Loop")) {
			translation = "Loop";
		} else if (text.equals("bpm")) {
			translation = "bpm";
		} else if (text.equals("Up")) {
			translation = "Arriba";
		} else if (text.equals("Down")) {
			translation = "Abajo";
		} else if (text.equals("Record")) {
			translation = "Grabar";
		} else if (text.equals("Stop")) {
			translation = "Parar";
		} else if (text.equals("Replay")) {
			translation = "Repetir";

		} else if (text.equals("None")) {
			translation = "Nada";
		} else if (text.equals("Both")) {
			translation = "Ambos";
		} else if (text.equals("Vibrato")) {
			translation = "Vibrato";
		} else if (text.equals("Pitch")) {
			translation = "Tono";
		} else if (text.equals("Volume+Filter")) {
			translation = "Volumen + Filtro";
		} else if (text.equals("C")) {
			translation = "Do";
		} else if (text.equals("Db")) {
			translation = "Re bemol";
		} else if (text.equals("D")) {
			translation = "Re";
		} else if (text.equals("Eb")) {
			translation = "Mi bemol";
		} else if (text.equals("E")) {
			translation = "Mi";
		} else if (text.equals("F")) {
			translation = "Fa";
		} else if (text.equals("Gb")) {
			translation = "Sol bemol";
		} else if (text.equals("G")) {
			translation = "Sol";
		} else if (text.equals("Ab")) {
			translation = "La bemol";
		} else if (text.equals("A")) {
			translation = "La";
		} else if (text.equals("Bb")) {
			translation = "Si bemol";
		} else if (text.equals("B")) {
			translation = "Si";
		} else if (text.equals("Low Pass")) {
			translation = "Paso bajo";
		} else if (text.equals("Band Pass")) {
			translation = "Paso de banda";
		} else if (text.equals("High Pass")) {
			translation = "Paso alto";
		} else if (text.equals("Fade")) {
			translation = "Fundido";
		} else if (text.equals("Comb 1")) {
			translation = "Peine 1";
		} else if (text.equals("Comb 2")) {
			translation = "Peine 2";
		} else if (text.equals("Comb 3")) {
			translation = "Peine 3";
		} else if (text.equals("Comb 4")) {
			translation = "Peine 4";
		} else if (text.equals("Formant 1")) {
			translation = "Formantes 1";
		} else if (text.equals("Formant 2")) {
			translation = "Formantes 2";
		} else if (text.equals("Formant 3")) {
			translation = "Formantes 3";
		} else if (text.equals("Formant 4")) {
			translation = "Formantes 4";
		} else if (text.equals("Wave")) {
			translation = "Ola";
		} else if (text.equals("Ramp Up")) {
			translation = "Rampa hacia arriba";
		} else if (text.equals("Ramp Down")) {
			translation = "Rampa hacia abajo";
		} else if (text.equals("Two Tone")) {
			translation = "Dos tonos";
		} else if (text.equals("Noise")) {
			translation = "Ruido";
		} else if (text.equals("Pulse 1/4")) {
			translation = "Pulso 1/4";
		} else if (text.equals("Pulse 1/8")) {
			translation = "Pulso 1/8";
		} else if (text.equals("Pulse 1/16")) {
			translation = "Pulso 1/16";
		} else if (text.equals("Mod Pitch")) {
			translation = "Mod el tono";
		} else if (text.equals("Mod Amp")) {
			translation = "Mod la amp";

		} else if (text.equals("Save sound as")) {
			translation = "Guardar sonido";
		} else if (text.equals("Delete sound?")) {
			translation = "Borrar sonido?";
		} else if (text.equals("Are you sure you want to quit?")) {
			translation = "¿Seguro que quieres salir?";
		} else if (text.equals("Save Recording")) {
			translation = "Guardar grabación";
		} else if (text.equals("Recording name:")) {
			translation = "Nombre de la grabación:";

			// translations for settings panel
		} else if (text.equals("Settings")) {
			translation = "Ajustes";
		} else if (text.equals("Keyboard size")) {
			translation = "Tamaño del teclado";
		} else if (text.equals("Background")) {
			translation = "Fondo";
		} else if (text.equals("Choose")) {
			translation = "Escoger";
		} else if (text.equals("MIDI channel")) {
			translation = "MIDI canal";
		} else if (text.equals("Tuning type")) {
			translation = "Clase de afinar";
		} else if (text.equals("Tuning cents")) {
			translation = "Afinar en cents";
		} else if (text.equals("Any")) {
			translation = "Cualquier canal";
		} else if (text.equals("Equal Temperament")) {
			translation = "Temperamento igual";
		} else if (text.equals("Just Intonation")) {
			translation = "Entonación justo";
		} else if (text.equals("Out of Tune")) {
			translation = "Fuera de tono";
		} else if (text.equals("Choose a background")) {
			translation = "Elija un fondo";

		} else if (text.equals("Onyx")) {
			translation = "Onyx";
		} else if (text.equals("Metal")) {
			translation = "Metal";
		} else if (text.equals("Wood")) {
			translation = "Leña";
		} else if (text.equals("Aura")) {
			translation = "Aura";
		} else if (text.equals("Ice")) {
			translation = "Helado";
		} else if (text.equals("Tea")) {
			translation = "Té";
		} else if (text.equals("Space")) {
			translation = "Espacio";
		} else if (text.equals("Sunset")) {
			translation = "Puesta de sol";
		} else if (text.equals("Tropical")) {
			translation = "Tropical";

		} else {
			translation = super.translateSpanish(text);
		}
		return translation;
	}

	@Override
	public String translateFrench(String text) {
		String translation = text;
		if (text.equals("EasySynth")) {
			translation = "EasySynth";
		}

		// Translations for main panel
		else if (text.equals("Instrument")) {
			translation = "Instrument";
		} else if (text.equals("Sound")) {
			translation = "Sonner";
		} else if (text.equals("Harmonics")) {
			translation = "Harmoniques";
		} else if (text.equals("Filter")) {
			translation = "Filtre";
		} else if (text.equals("Amp")) {
			translation = "Amp";
		} else if (text.equals("Mod")) {
			translation = "Mod";
		} else if (text.equals("Mod2")) {
			translation = "Mod2";
		} else if (text.equals("Echo")) {
			translation = "Echo";
		} else if (text.equals("Arpeggio")) {
			translation = "Arpegio";
		} else if (text.equals("Sequencer")) {
			translation = "Séquenceur";
		} else if (text.equals("Record")) {
			translation = "Enregistrer";
		} else if (text.equals("Type")) {
			translation = "Classe";
		} else if (text.equals("Shape")) {
			translation = "Forme";
		} else if (text.equals("Amount")) {
			translation = "Montant";
		} else if (text.equals("Rate")) {
			translation = "Vitesse";
		} else if (text.equals("Sync")) {
			translation = "Sync";
		} else if (text.equals("Attack")) {
			translation = "Attaque";
		} else if (text.equals("Decay")) {
			translation = " Déclin";
		} else if (text.equals("Sustain")) {
			translation = "Soutenir";
		} else if (text.equals("Release")) {
			translation = "Relâcher";
		} else if (text.equals("Save")) {
			translation = "Enregistrez";
		} else if (text.equals("Save Recording")) {
			translation = "Sauvegarder l'enregistrement";
		} else if (text.equals("Recording name:")) {
			translation = "Nom d'enregistrement:";
		} else if (text.equals("Saved to")) {
			translation = "Enregistré à";
		} else if (text.equals("Failed to save to")) {
			translation = "Échec de l'enregistrement à";
		} else if (text.equals("Send")) {
			translation = "Envoyer";
		} else if (text.equals("Delete")) {
			translation = "Effacer";
		} else if (text.equals("Voices")) {
			translation = "Voix";
		} else if (text.equals("Mono")) {
			translation = "Mono";
		} else if (text.equals("Poly")) {
			translation = "Poli";
		} else if (text.equals("Chorus")) {
			translation = "Chorale";
		} else if (text.equals("Chorus Width")) {
			translation = "Largeur Choir";
		} else if (text.equals("Portamento")) {
			translation = "Portamento";
		} else if (text.equals("Velocity")) {
			translation = "Vitesse";
		} else if (text.equals("Expression")) {
			translation = "Expression";
		} else if (text.equals("Octave")) {
			translation = "Octave";
		} else if (text.equals("Hold")) {
			translation = "Tenier";
		} else if (text.equals("Key")) {
			translation = "Touche";
		} else if (text.equals("Resonance")) {
			translation = "Resonance";
		} else if (text.equals("Range")) {
			translation = "Plage";
		} else if (text.equals("low")) {
			translation = "faible";
		} else if (text.equals("high")) {
			translation = "haut";
		} else if (text.equals("Volume")) {
			translation = "Volume";
		} else if (text.equals("Overdrive")) {
			translation = "Overdrive";
		} else if (text.equals("per Voice")) {
			translation = "par voix";
		} else if (text.equals("Amount")) {
			translation = "Montant";
		} else if (text.equals("Delay")) {
			translation = "Retard";
		} else if (text.equals("Feedback")) {
			translation = "Rétroaction";
		} else if (text.equals("Flange")) {
			translation = "Bride";
		} else if (text.equals("Glide")) {
			translation = "Glisser";
		} else if (text.equals("Note")) {
			translation = "Nota";
		} else if (text.equals("Loop")) {
			translation = "Boucle";
		} else if (text.equals("bpm")) {
			translation = "bpm";
		} else if (text.equals("Up")) {
			translation = "Haut";
		} else if (text.equals("Down")) {
			translation = "Bas";
		} else if (text.equals("Stop")) {
			translation = "Arrêtez";
		} else if (text.equals("Replay")) {
			translation = "Rejouer";

		} else if (text.equals("None")) {
			translation = "Rien";
		} else if (text.equals("Both")) {
			translation = "Les deux";
		} else if (text.equals("Vibrato")) {
			translation = "Vibrato";
		} else if (text.equals("Pitch")) {
			translation = "Hauteur";
		} else if (text.equals("Volume+Filter")) {
			translation = "Volume + Filtre";
		} else if (text.equals("C")) {
			translation = "Do";
		} else if (text.equals("Db")) {
			translation = "Ré bémol";
		} else if (text.equals("D")) {
			translation = "Ré";
		} else if (text.equals("Eb")) {
			translation = "Mi bémol";
		} else if (text.equals("E")) {
			translation = "Mi";
		} else if (text.equals("F")) {
			translation = "Fa";
		} else if (text.equals("Gb")) {
			translation = "Sol bémol";
		} else if (text.equals("G")) {
			translation = "Sol";
		} else if (text.equals("Ab")) {
			translation = "La bémol";
		} else if (text.equals("A")) {
			translation = "La";
		} else if (text.equals("Bb")) {
			translation = "Si bémol";
		} else if (text.equals("B")) {
			translation = "Si";
		} else if (text.equals("Low Pass")) {
			translation = "Passe-bas";
		} else if (text.equals("Band Pass")) {
			translation = "Passe-bande";
		} else if (text.equals("High Pass")) {
			translation = "Passe-haut";
		} else if (text.equals("Fade")) {
			translation = "Fade";
		} else if (text.equals("Comb 1")) {
			translation = "Peigne 1";
		} else if (text.equals("Comb 2")) {
			translation = "Peigne 2";
		} else if (text.equals("Comb 3")) {
			translation = "Peigne 3";
		} else if (text.equals("Comb 4")) {
			translation = "Peigne 4";
		} else if (text.equals("Formant 1")) {
			translation = "Formant 1";
		} else if (text.equals("Formant 2")) {
			translation = "Formant 2";
		} else if (text.equals("Formant 3")) {
			translation = "Formant 3";
		} else if (text.equals("Formant 4")) {
			translation = "Formant 4";
		} else if (text.equals("Wave")) {
			translation = "Vague";
		} else if (text.equals("Ramp Up")) {
			translation = "Rampe montante";
		} else if (text.equals("Ramp Down")) {
			translation = "Rampe de descente";
		} else if (text.equals("Two Tone")) {
			translation = "Deux tons";
		} else if (text.equals("Noise")) {
			translation = "Bruit";
		} else if (text.equals("Pulse 1/4")) {
			translation = "Pulse 1/4";
		} else if (text.equals("Pulse 1/8")) {
			translation = "Pulse 1/8";
		} else if (text.equals("Pulse 1/16")) {
			translation = "Pulse 1/16";
		} else if (text.equals("Mod Pitch")) {
			translation = "Modifier hauteur";
		} else if (text.equals("Mod Amp")) {
			translation = "Modifier l'amplitude";

		} else if (text.equals("Save sound as")) {
			translation = "Enregistrer le son comme";
		} else if (text.equals("Delete sound?")) {
			translation = "Supprimer le son?";
		} else if (text.equals("Are you sure you want to quit?")) {
			translation = "Êtes-vous sûr de vouloir quitter?";
		} else if (text.equals("Save Recording")) {
			translation = "Sauvegarder l'enregistrement";
		} else if (text.equals("Recording name:")) {
			translation = "Nom de l'enregistrement:";

			// translations for settings panel
		} else if (text.equals("Settings")) {
			translation = "Paramètres";
		} else if (text.equals("Keyboard size")) {
			translation = "Taille du clavier";
		} else if (text.equals("Background")) {
			translation = "Fond";
		} else if (text.equals("Choose")) {
			translation = "Choisir";
		} else if (text.equals("MIDI channel")) {
			translation = "MIDI canal";
		} else if (text.equals("Tuning type")) {
			translation = "Classe de réglage";
		} else if (text.equals("Tuning cents")) {
			translation = "Réglage fin";
		} else if (text.equals("Any")) {
			translation = "Un canal";
		} else if (text.equals("Equal Temperament")) {
			translation = "Tempérament égal";
		} else if (text.equals("Just Intonation")) {
			translation = "Intonation juste";
		} else if (text.equals("Out of Tune")) {
			translation = "Désaccordé";
		} else if (text.equals("Choose a background")) {
			translation = "Choisir un fond";

		} else if (text.equals("Onyx")) {
			translation = "Onyx";
		} else if (text.equals("Metal")) {
			translation = "Métal";
		} else if (text.equals("Wood")) {
			translation = "Bois";
		} else if (text.equals("Aura")) {
			translation = "Aura";
		} else if (text.equals("Ice")) {
			translation = "Glace";
		} else if (text.equals("Tea")) {
			translation = "Thé";
		} else if (text.equals("Space")) {
			translation = "Cosmos";
		} else if (text.equals("Sunset")) {
			translation = "Coucher du soleil";
		} else if (text.equals("Tropical")) {
			translation = "Tropical";

		} else {
			translation = super.translateFrench(text);
		}
		return translation;
	}

	@Override
	public String translateGerman(String text) {
		String translation = text;
		if (text.equals("EasySynth")) {
			translation = "EasySynth";
		}

		// Translations for main panel
		else if (text.equals("Instrument")) {
			translation = "Instrument";
		} else if (text.equals("Sound")) {
			translation = "Klang";
		} else if (text.equals("Harmonics")) {
			translation = "Harmonik";
		} else if (text.equals("Filter")) {
			translation = "Filter";
		} else if (text.equals("Amp")) {
			translation = "Amp";
		} else if (text.equals("Mod")) {
			translation = "Mod";
		} else if (text.equals("Mod2")) {
			translation = "Mod2";
		} else if (text.equals("Echo")) {
			translation = "Echo";
		} else if (text.equals("Arpeggio")) {
			translation = "Arpeggio";
		} else if (text.equals("Sequencer")) {
			translation = "Sequenzer";
		} else if (text.equals("Record")) {
			translation = "Aufnehmen";
		} else if (text.equals("Type")) {
			translation = "Klasse";
		} else if (text.equals("Shape")) {
			translation = "Form";
		} else if (text.equals("Amount")) {
			translation = "Stärke";
		} else if (text.equals("Rate")) {
			translation = "Rate";
		} else if (text.equals("Sync")) {
			translation = "Sync";
		} else if (text.equals("Attack")) {
			translation = "Anschwellzeit";
		} else if (text.equals("Decay")) {
			translation = " Abklingzeit";
		} else if (text.equals("Sustain")) {
			translation = "Dauerpegel";
		} else if (text.equals("Release")) {
			translation = "Ausklingphase";
		} else if (text.equals("Save")) {
			translation = "Speichern";
		} else if (text.equals("Save Recording")) {
			translation = "Aufnahme speichern";
		} else if (text.equals("Recording name:")) {
			translation = "Aufnahmename :";
		} else if (text.equals("Saved to")) {
			translation = "gespeichert zu";
		} else if (text.equals("Failed to save to")) {
			translation = "Fehler beim speichern";
		} else if (text.equals("Send")) {
			translation = "Senden";
		} else if (text.equals("Delete")) {
			translation = "Löschen";
		} else if (text.equals("Voices")) {
			translation = "Stimmen";
		} else if (text.equals("Mono")) {
			translation = "Mono";
		} else if (text.equals("Poly")) {
			translation = "Poly";
		} else if (text.equals("Chorus")) {
			translation = "Chor";
		} else if (text.equals("Chorus Width")) {
			translation = "Chorbreite";
		} else if (text.equals("Portamento")) {
			translation = "Portamento";
		} else if (text.equals("Velocity")) {
			translation = "Dynamik ";
		} else if (text.equals("Expression")) {
			translation = "Ausdruck";
		} else if (text.equals("Octave")) {
			translation = "Oktave";
		} else if (text.equals("Hold")) {
			translation = "Halten";
		} else if (text.equals("Key")) {
			translation = "Schlüssel";
		} else if (text.equals("Resonance")) {
			translation = "Resonanz";
		} else if (text.equals("Range")) {
			translation = "Bereich";
		} else if (text.equals("low")) {
			translation = "niedrig";
		} else if (text.equals("high")) {
			translation = "hoch";
		} else if (text.equals("Volume")) {
			translation = "Lautstärke";
		} else if (text.equals("Overdrive")) {
			translation = "Übersteuerung";
		} else if (text.equals("per Voice")) {
			translation = "pro Stimme";
		} else if (text.equals("Amount")) {
			translation = "Höhe";
		} else if (text.equals("Delay")) {
			translation = "Verzögerung";
		} else if (text.equals("Feedback")) {
			translation = "Rückkopplung";
		} else if (text.equals("Flange")) {
			translation = "Flansch";
		} else if (text.equals("Rate")) {
			translation = "Rate";
		} else if (text.equals("Glide")) {
			translation = "Gleiten";
		} else if (text.equals("Note")) {
			translation = "Note";
		} else if (text.equals("Loop")) {
			translation = "Schleife";
		} else if (text.equals("bpm")) {
			translation = "bpm";
		} else if (text.equals("Up")) {
			translation = "Oben";
		} else if (text.equals("Down")) {
			translation = "Unten";
		} else if (text.equals("Record")) {
			translation = "Aufnehmen";
		} else if (text.equals("Stop")) {
			translation = "Stopp";
		} else if (text.equals("Replay")) {
			translation = "Wiederholung";

		} else if (text.equals("None")) {
			translation = "Keine";
		} else if (text.equals("Both")) {
			translation = "Beide";
		} else if (text.equals("Vibrato")) {
			translation = "Vibrato";
		} else if (text.equals("Pitch")) {
			translation = "Tonhöhe";
		} else if (text.equals("Volume+Filter")) {
			translation = "Volumen + Filter";
		} else if (text.equals("C")) {
			translation = "C";
		} else if (text.equals("Db")) {
			translation = "Des";
		} else if (text.equals("D")) {
			translation = "D";
		} else if (text.equals("Eb")) {
			translation = "Es";
		} else if (text.equals("E")) {
			translation = "E";
		} else if (text.equals("F")) {
			translation = "F";
		} else if (text.equals("Gb")) {
			translation = "Ges";
		} else if (text.equals("G")) {
			translation = "G";
		} else if (text.equals("Ab")) {
			translation = "As";
		} else if (text.equals("A")) {
			translation = "A";
		} else if (text.equals("Bb")) {
			translation = "B";
		} else if (text.equals("B")) {
			translation = "H";
		} else if (text.equals("Low Pass")) {
			translation = "Tiefpass";
		} else if (text.equals("Band Pass")) {
			translation = "Bandpass";
		} else if (text.equals("High Pass")) {
			translation = "Hochpass";
		} else if (text.equals("Fade")) {
			translation = "Ausblenden";
		} else if (text.equals("Comb 1")) {
			translation = "Kamm 1";
		} else if (text.equals("Comb 2")) {
			translation = "Kamm 2";
		} else if (text.equals("Comb 3")) {
			translation = "Kamm 3";
		} else if (text.equals("Comb 4")) {
			translation = "Kamm 4";
		} else if (text.equals("Formant 1")) {
			translation = "Formanten 1";
		} else if (text.equals("Formant 2")) {
			translation = "Formanten 2";
		} else if (text.equals("Formant 3")) {
			translation = "Formanten 3";
		} else if (text.equals("Formant 4")) {
			translation = "Formanten 4";
		} else if (text.equals("Wave")) {
			translation = "Wellenform";
		} else if (text.equals("Ramp Up")) {
			translation = "Rampe oben";
		} else if (text.equals("Ramp Down")) {
			translation = "Rampe unten";
		} else if (text.equals("Two Tone")) {
			translation = "Ton zwei";
		} else if (text.equals("Noise")) {
			translation = "Rauschen";
		} else if (text.equals("Pulse 1/4")) {
			translation = "Puls 1/4";
		} else if (text.equals("Pulse 1/8")) {
			translation = "Puls 1/8";
		} else if (text.equals("Pulse 1/16")) {
			translation = "Puls 1/16";
		} else if (text.equals("Mod Pitch")) {
			translation = "Mod Ton";
		} else if (text.equals("Mod Amp")) {
			translation = "Mod Amp";

		} else if (text.equals("Save sound as")) {
			translation = "Klang speichern unter";
		} else if (text.equals("Delete sound?")) {
			translation = "Klang löschen?";
		} else if (text.equals("Are you sure you want to quit?")) {
			translation = "Sind Sie sicher, dass Sie aufhören wollen?";
		} else if (text.equals("Save Recording")) {
			translation = "Aufnahme speichern";
		} else if (text.equals("Recording name:")) {
			translation = "Aufzeichnung Namen:";

			// translations for settings panel
		} else if (text.equals("Settings")) {
			translation = "Einstellungen";
		} else if (text.equals("Keyboard size")) {
			translation = "Tastaturgröße";
		} else if (text.equals("Background")) {
			translation = "Hintergrund";
		} else if (text.equals("Choose")) {
			translation = "Wählen";
		} else if (text.equals("MIDI channel")) {
			translation = "MIDI Kanal";
		} else if (text.equals("Tuning type")) {
			translation = "Tuning- Klasse";
		} else if (text.equals("Tuning cents")) {
			translation = "Feinabstimmung";
		} else if (text.equals("Any")) {
			translation = "Irgendeiner";
		} else if (text.equals("Equal Temperament")) {
			translation = "Gleichschwebende Stimmung";
		} else if (text.equals("Just Intonation")) {
			translation = "Nur Intonation";
		} else if (text.equals("Out of Tune")) {
			translation = "Verstimmt";
		} else if (text.equals("Choose a background")) {
			translation = "Wählen Sie ein Hintergrund";

		} else if (text.equals("Onyx")) {
			translation = "Onyx";
		} else if (text.equals("Metal")) {
			translation = "Metall";
		} else if (text.equals("Wood")) {
			translation = "Holz";
		} else if (text.equals("Aura")) {
			translation = "Aura";
		} else if (text.equals("Ice")) {
			translation = "Eis";
		} else if (text.equals("Tea")) {
			translation = "Tee";
		} else if (text.equals("Space")) {
			translation = "Weltraum";
		} else if (text.equals("Sunset")) {
			translation = "Sonnenuntergang";
		} else if (text.equals("Tropical")) {
			translation = "Tropisch";

		} else {
			translation = super.translateGerman(text);
		}
		return translation;
	}

	@Override
	public String translateRussian(String text) {
		String translation = text;
		if (text.equals("EasySynth")) {
			translation = "EasySynth";
		}

		// translations for main panel
		else if (text.equals("Instrument")) {
			translation = "ин�?трумент";
		} else if (text.equals("Sound")) {
			translation = "звук";
		} else if (text.equals("Harmonics")) {
			translation = "Гармоники";
		} else if (text.equals("Filter")) {
			translation = "фильтр";
		} else if (text.equals("Amp")) {
			translation = "ампер";
		} else if (text.equals("Mod")) {
			translation = "мод";
		} else if (text.equals("Mod2")) {
			translation = "мод2";
		} else if (text.equals("Echo")) {
			translation = "�?хо";
		} else if (text.equals("Arpeggio")) {
			translation = "арпеджио";
		} else if (text.equals("Sequencer")) {
			translation = "�?еквен�?ор";
		} else if (text.equals("Record")) {
			translation = "запи�?ь";
		} else if (text.equals("Type")) {
			translation = "Кла�?�?";
		} else if (text.equals("Shape")) {
			translation = "форма";
		} else if (text.equals("Amount")) {
			translation = "Количе�?тво";
		} else if (text.equals("Rate")) {
			translation = "Ставка";
		} else if (text.equals("Sync")) {
			translation = "Синх";
		} else if (text.equals("Attack")) {
			translation = "�?така";
		} else if (text.equals("Decay")) {
			translation = "ра�?пад";
		} else if (text.equals("Save")) {
			translation = "Сохранить";
		} else if (text.equals("Save Recording")) {
			translation = "Сохранить запи�?ь";
		} else if (text.equals("Recording name:")) {
			translation = "Запи�?ь им�? :";
		} else if (text.equals("Saved to")) {
			translation = "Добавлено в";
		} else if (text.equals("Failed to save to")) {
			translation = "не удало�?ь �?охранить";
		} else if (text.equals("Send")) {
			translation = "по�?лать";
		} else if (text.equals("Delete")) {
			translation = "Удалить";
		} else if (text.equals("Voices")) {
			translation = "Голо�?а";
		} else if (text.equals("Mono")) {
			translation = "моно";
		} else if (text.equals("Poly")) {
			translation = "Поли";
		} else if (text.equals("Chorus")) {
			translation = "хор";
		} else if (text.equals("Chorus Width")) {
			translation = "Ширина хора";
		} else if (text.equals("Portamento")) {
			translation = "принима�?";
		} else if (text.equals("Velocity")) {
			translation = "�?коро�?ть";
		} else if (text.equals("Expression")) {
			translation = "выражение";
		} else if (text.equals("Octave")) {
			translation = "октава";
		} else if (text.equals("Hold")) {
			translation = "Держать";
		} else if (text.equals("Key")) {
			translation = "ключ";
		} else if (text.equals("Resonance")) {
			translation = "резонан�?";
		} else if (text.equals("Range")) {
			translation = "�?�?�?ортимент";
		} else if (text.equals("low")) {
			translation = "низкий";
		} else if (text.equals("high")) {
			translation = "вы�?ока�?";
		} else if (text.equals("Attack")) {
			translation = "�?така";
		} else if (text.equals("Decay")) {
			translation = "ра�?пад";
		} else if (text.equals("Sustain")) {
			translation = "поддерживать";
		} else if (text.equals("Release")) {
			translation = "Выпу�?к";
		} else if (text.equals("Volume")) {
			translation = "объем";
		} else if (text.equals("Overdrive")) {
			translation = "овердрайв";
		} else if (text.equals("per Voice")) {
			translation = "за голо�?а";
		} else if (text.equals("Shape")) {
			translation = "форма";
		} else if (text.equals("Amount")) {
			translation = "Количе�?тво";
		} else if (text.equals("Rate")) {
			translation = "Ставка";
		} else if (text.equals("Sync")) {
			translation = "Синх";
		} else if (text.equals("Delay")) {
			translation = "задержка";
		} else if (text.equals("Feedback")) {
			translation = "Обратна�? �?в�?зь";
		} else if (text.equals("Flange")) {
			translation = "фланец";
		} else if (text.equals("Glide")) {
			translation = "�?кольжение";
		} else if (text.equals("Note")) {
			translation = "Заметка";
		} else if (text.equals("Loop")) {
			translation = "петл�?";
		} else if (text.equals("bpm")) {
			translation = "Чтм";
		} else if (text.equals("Up")) {
			translation = "вверх";
		} else if (text.equals("Down")) {
			translation = "вниз";
		} else if (text.equals("Record")) {
			translation = "запи�?ь";
		} else if (text.equals("Stop")) {
			translation = "Стоп";
		} else if (text.equals("Replay")) {
			translation = "переигровка";
		} else if (text.equals("Save")) {
			translation = "Сохранить";

		} else if (text.equals("None")) {
			translation = "�?и одной";
		} else if (text.equals("Both")) {
			translation = "И то и другое";
		} else if (text.equals("Vibrato")) {
			translation = "вибрато";
		} else if (text.equals("Pitch")) {
			translation = "Подача";
		} else if (text.equals("Volume+Filter")) {
			translation = "Громко�?ть + фильтр";
		} else if (text.equals("C")) {
			translation = "До";
		} else if (text.equals("Db")) {
			translation = "Ре-бемоль";
		} else if (text.equals("D")) {
			translation = "Ре";
		} else if (text.equals("Eb")) {
			translation = "Ми-бемоль";
		} else if (text.equals("E")) {
			translation = "Ми";
		} else if (text.equals("F")) {
			translation = "Фа";
		} else if (text.equals("Gb")) {
			translation = "Соль-бемоль";
		} else if (text.equals("G")) {
			translation = "Соль";
		} else if (text.equals("Ab")) {
			translation = "Л�?-бемоль";
		} else if (text.equals("A")) {
			translation = "Л�?";
		} else if (text.equals("Bb")) {
			translation = "Си-бемоль";
		} else if (text.equals("B")) {
			translation = "Си";
		} else if (text.equals("Low Pass")) {
			translation = "�?изкий Проходить";
		} else if (text.equals("Band Pass")) {
			translation = "Группа Проходить";
		} else if (text.equals("High Pass")) {
			translation = "Вы�?ока�? Проходить";
		} else if (text.equals("Fade")) {
			translation = "ув�?дать";
		} else if (text.equals("Comb 1")) {
			translation = "гребень 1";
		} else if (text.equals("Comb 2")) {
			translation = "гребень 2";
		} else if (text.equals("Comb 3")) {
			translation = "гребень 3";
		} else if (text.equals("Comb 4")) {
			translation = "гребень 4";
		} else if (text.equals("Formant 1")) {
			translation = "формант 1";
		} else if (text.equals("Formant 2")) {
			translation = "формант 2";
		} else if (text.equals("Formant 3")) {
			translation = "формант 3";
		} else if (text.equals("Formant 4")) {
			translation = "формант 4";
		} else if (text.equals("Wave")) {
			translation = "Волна";
		} else if (text.equals("Ramp Up")) {
			translation = "�?ара�?тить";
		} else if (text.equals("Ramp Down")) {
			translation = "Замедлени�?";
		} else if (text.equals("Two Tone")) {
			translation = "Две тонны";
		} else if (text.equals("Noise")) {
			translation = "Шум";
		} else if (text.equals("Pulse 1/4")) {
			translation = "Пуль�? 1/4";
		} else if (text.equals("Pulse 1/8")) {
			translation = "Пуль�? 1/8";
		} else if (text.equals("Pulse 1/16")) {
			translation = "Пуль�? 1/16";
		} else if (text.equals("Mod Pitch")) {
			translation = "мод Подача";
		} else if (text.equals("Mod Amp")) {
			translation = "мод ампер";

		} else if (text.equals("Save sound as")) {
			translation = "Сохранить звук";
		} else if (text.equals("Delete sound?")) {
			translation = "Удалить звук ?";
		} else if (text.equals("Are you sure you want to quit?")) {
			translation = "Вы уверены, что хотите выйти?";
		} else if (text.equals("Save Recording")) {
			translation = "Сохранить запи�?ь";
		} else if (text.equals("Recording name:")) {
			translation = "Запи�?ь им�?:";

			// translations for settings
		} else if (text.equals("Settings")) {
			translation = "на�?тройки";
		} else if (text.equals("Keyboard size")) {
			translation = "размер клавиатуры";
		} else if (text.equals("Background")) {
			translation = "Задний план";
		} else if (text.equals("Choose")) {
			translation = "Выберите";
		} else if (text.equals("MIDI channel")) {
			translation = "MIDI канал";
		} else if (text.equals("Tuning type")) {
			translation = "кла�?�? тюнинг";
		} else if (text.equals("Tuning cents")) {
			translation = "Тюнинг центов";
		} else if (text.equals("Any")) {
			translation = "Любой канал";
		} else if (text.equals("Equal Temperament")) {
			translation = "Равно Темперамент";
		} else if (text.equals("Just Intonation")) {
			translation = "Про�?то Интонаци�?";
		} else if (text.equals("Out of Tune")) {
			translation = "Ра�?�?троенный";
		} else if (text.equals("Choose a background")) {
			translation = "Выберите фон";

		} else if (text.equals("Onyx")) {
			translation = "оник�?";
		} else if (text.equals("Metal")) {
			translation = "металл";
		} else if (text.equals("Wood")) {
			translation = "Дерево";
		} else if (text.equals("Aura")) {
			translation = "аура";
		} else if (text.equals("Ice")) {
			translation = "лед";
		} else if (text.equals("Tea")) {
			translation = "Чай";
		} else if (text.equals("Space")) {
			translation = "Ко�?мо�?";
		} else if (text.equals("Sunset")) {
			translation = "Закат �?олнца";
		} else if (text.equals("Tropical")) {
			translation = "тропиче�?кий";

		} else {
			translation = super.translateRussian(text);
		}
		return translation;
	}

}

