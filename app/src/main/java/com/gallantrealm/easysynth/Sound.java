package com.gallantrealm.easysynth;

import java.io.Serializable;
import org.json.JSONObject;
import org.json.JSONArray;

public class Sound implements Serializable {

	public static final int VELOCITY_NONE = 0;
	public static final int VELOCITY_VOLUME = 1;
	public static final int VELOCITY_FILTER = 2;
	public static final int VELOCITY_BOTH = 3;

	private static final long serialVersionUID = 1L;
	boolean zeroAdjustedEnvelopes;

	int mode; // 0=mono, 1=poly, 2=chorus
	int octave;
	int key;
	boolean chorusWidthSet;
	float chorusWidth;
	float portamentoAmount;
	int velocityType;
	int expressionType;
	boolean hold;

	float[] harmonics;

	int filterType;
	float filterResonance;
	float filterCutoff;
	float filterEnvAmount;
	float filterEnvAttack;
	float filterEnvDecay;
	float filterEnvSustain;
	float filterEnvRelease;

	float ampAttack;
	float ampDecay;
	float ampSustain;
	float ampRelease;
	int ampOverdrive;
	boolean ampOverdrivePerVoice;

	int vibratoDestination;
	int vibratoType;
	float vibratoAmount;
	float vibratoRate;
	boolean vibratoSync;
	boolean vibratoEnvelopeSet;
	float vibratoAttack;
	float vibratoDecay;

	int vibrato2Destination;
	int vibrato2Type;
	float vibrato2Amount;
	float vibrato2Rate;
	boolean vibrato2Sync;
	boolean vibrato2EnvelopeSet;
	float vibrato2Attack;
	float vibrato2Decay;

	float echoAmount;
	float echoDelay;
	float echoFeedback;
	float flangeAmount;
	float flangeRate;

	float distortionAmount;
	float bitCrushAmount;
	float whiteNoiseAmount;
	float pinkNoiseAmount;
	float brownNoiseAmount;
	float staticNoiseAmount;

	float reverbAmount;
	float limiterAmount;
	float compressorAmount;

	int[] sequence;
	int[] sequenceVelocity; // Per-step velocity (0-100)
	boolean[] sequenceMute; // Per-step mute flag
	float sequenceRate;
	boolean sequenceLoop;
	boolean bpmSequenceRate;
	int currentSequenceStep; // For visual feedback

	float ampVolume;
	
	String author; // Author of the instrument

	// JSON serialization methods for .wavesynth files
	public static Sound fromJSON(String jsonString) throws Exception {
		JSONObject json = new JSONObject(jsonString);
		Sound sound = new Sound();
		
		sound.mode = json.optInt("mode", 1);
		sound.octave = json.optInt("octave", 3);
		sound.key = json.optInt("key", 0);
		sound.chorusWidthSet = json.optBoolean("chorusWidthSet", true);
		sound.chorusWidth = (float) json.optDouble("chorusWidth", 0.1);
		sound.portamentoAmount = (float) json.optDouble("portamentoAmount", 0.0);
		sound.velocityType = json.optInt("velocityType", 0);
		sound.expressionType = json.optInt("expressionType", 0);
		sound.hold = json.optBoolean("hold", false);
		
		// Harmonics
		JSONArray harmonicsArray = json.optJSONArray("harmonics");
		if (harmonicsArray != null) {
			sound.harmonics = new float[harmonicsArray.length()];
			for (int i = 0; i < harmonicsArray.length(); i++) {
				sound.harmonics[i] = (float) harmonicsArray.optDouble(i, 0.0);
			}
		} else {
			sound.harmonics = new float[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		}
		
		// Filter
		sound.filterType = json.optInt("filterType", 0);
		sound.filterResonance = (float) json.optDouble("filterResonance", 0.0);
		sound.filterCutoff = (float) json.optDouble("filterCutoff", 0.0);
		sound.filterEnvAmount = (float) json.optDouble("filterEnvAmount", 0.0);
		sound.filterEnvAttack = (float) json.optDouble("filterEnvAttack", 0.0);
		sound.filterEnvDecay = (float) json.optDouble("filterEnvDecay", 100.0);
		sound.filterEnvSustain = (float) json.optDouble("filterEnvSustain", 0.0);
		sound.filterEnvRelease = (float) json.optDouble("filterEnvRelease", 0.0);
		
		// Amp
		sound.ampAttack = (float) json.optDouble("ampAttack", 0.0);
		sound.ampDecay = (float) json.optDouble("ampDecay", 100.0);
		sound.ampSustain = (float) json.optDouble("ampSustain", 0.0);
		sound.ampRelease = (float) json.optDouble("ampRelease", 0.0);
		sound.ampOverdrive = json.optInt("ampOverdrive", 0);
		sound.ampOverdrivePerVoice = json.optBoolean("ampOverdrivePerVoice", false);
		sound.ampVolume = (float) json.optDouble("ampVolume", 0.5);
		
		// Vibrato
		sound.vibratoDestination = json.optInt("vibratoDestination", 0);
		sound.vibratoType = json.optInt("vibratoType", 0);
		sound.vibratoAmount = (float) json.optDouble("vibratoAmount", 0.0);
		sound.vibratoRate = (float) json.optDouble("vibratoRate", 0.0);
		sound.vibratoSync = json.optBoolean("vibratoSync", false);
		sound.vibratoEnvelopeSet = json.optBoolean("vibratoEnvelopeSet", true);
		sound.vibratoAttack = (float) json.optDouble("vibratoAttack", 0.0);
		sound.vibratoDecay = (float) json.optDouble("vibratoDecay", 999.0);
		
		// Vibrato 2
		sound.vibrato2Destination = json.optInt("vibrato2Destination", 0);
		sound.vibrato2Type = json.optInt("vibrato2Type", 0);
		sound.vibrato2Amount = (float) json.optDouble("vibrato2Amount", 0.0);
		sound.vibrato2Rate = (float) json.optDouble("vibrato2Rate", 0.0);
		sound.vibrato2Sync = json.optBoolean("vibrato2Sync", false);
		sound.vibrato2EnvelopeSet = json.optBoolean("vibrato2EnvelopeSet", true);
		sound.vibrato2Attack = (float) json.optDouble("vibrato2Attack", 0.0);
		sound.vibrato2Decay = (float) json.optDouble("vibrato2Decay", 0.0);
		
		// Effects
		sound.echoAmount = (float) json.optDouble("echoAmount", 0.0);
		sound.echoDelay = (float) json.optDouble("echoDelay", 0.0);
		sound.echoFeedback = (float) json.optDouble("echoFeedback", 0.0);
		sound.flangeAmount = (float) json.optDouble("flangeAmount", 0.0);
		sound.flangeRate = (float) json.optDouble("flangeRate", 0.0);
		
		// Harsh Noise Effects
		sound.distortionAmount = (float) json.optDouble("distortionAmount", 0.0);
		sound.bitCrushAmount = (float) json.optDouble("bitCrushAmount", 0.0);
		sound.whiteNoiseAmount = (float) json.optDouble("whiteNoiseAmount", 0.0);
		sound.pinkNoiseAmount = (float) json.optDouble("pinkNoiseAmount", 0.0);
		sound.brownNoiseAmount = (float) json.optDouble("brownNoiseAmount", 0.0);
		sound.staticNoiseAmount = (float) json.optDouble("staticNoiseAmount", 0.0);
		sound.reverbAmount = (float) json.optDouble("reverbAmount", 0.0);
		sound.limiterAmount = (float) json.optDouble("limiterAmount", 0.0);
		sound.compressorAmount = (float) json.optDouble("compressorAmount", 0.0);
		
		// Sequence
		JSONArray sequenceArray = json.optJSONArray("sequence");
		if (sequenceArray != null) {
			sound.sequence = new int[sequenceArray.length()];
			for (int i = 0; i < sequenceArray.length(); i++) {
				sound.sequence[i] = sequenceArray.optInt(i, 0);
			}
		} else {
			sound.sequence = new int[8];
		}
		
		// Sequence velocity (per-step)
		JSONArray sequenceVelocityArray = json.optJSONArray("sequenceVelocity");
		if (sequenceVelocityArray != null) {
			sound.sequenceVelocity = new int[sequenceVelocityArray.length()];
			for (int i = 0; i < sequenceVelocityArray.length(); i++) {
				sound.sequenceVelocity[i] = sequenceVelocityArray.optInt(i, 100);
			}
		} else {
			// Initialize with default velocity (100)
			sound.sequenceVelocity = new int[sound.sequence.length];
			for (int i = 0; i < sound.sequenceVelocity.length; i++) {
				sound.sequenceVelocity[i] = 100;
			}
		}
		
		// Sequence mute (per-step)
		JSONArray sequenceMuteArray = json.optJSONArray("sequenceMute");
		if (sequenceMuteArray != null) {
			sound.sequenceMute = new boolean[sequenceMuteArray.length()];
			for (int i = 0; i < sequenceMuteArray.length(); i++) {
				sound.sequenceMute[i] = sequenceMuteArray.optBoolean(i, false);
			}
		} else {
			// Initialize all unmuted
			sound.sequenceMute = new boolean[sound.sequence.length];
		}
		
		sound.sequenceRate = (float) json.optDouble("sequenceRate", 0.0);
		sound.sequenceLoop = json.optBoolean("sequenceLoop", false);
		sound.bpmSequenceRate = json.optBoolean("bpmSequenceRate", true);
		sound.currentSequenceStep = -1;
		
		// Metadata
		sound.author = json.optString("author", null);
		
		sound.zeroAdjustedEnvelopes = json.optBoolean("zeroAdjustedEnvelopes", true);
		
		return sound;
	}
	
	public String toJSON() throws Exception {
		JSONObject json = new JSONObject();
		
		json.put("mode", mode);
		json.put("octave", octave);
		json.put("key", key);
		json.put("chorusWidthSet", chorusWidthSet);
		json.put("chorusWidth", chorusWidth);
		json.put("portamentoAmount", portamentoAmount);
		json.put("velocityType", velocityType);
		json.put("expressionType", expressionType);
		json.put("hold", hold);
		
		// Harmonics
		JSONArray harmonicsArray = new JSONArray();
		if (harmonics != null) {
			for (float h : harmonics) {
				harmonicsArray.put(h);
			}
		}
		json.put("harmonics", harmonicsArray);
		
		// Filter
		json.put("filterType", filterType);
		json.put("filterResonance", filterResonance);
		json.put("filterCutoff", filterCutoff);
		json.put("filterEnvAmount", filterEnvAmount);
		json.put("filterEnvAttack", filterEnvAttack);
		json.put("filterEnvDecay", filterEnvDecay);
		json.put("filterEnvSustain", filterEnvSustain);
		json.put("filterEnvRelease", filterEnvRelease);
		
		// Amp
		json.put("ampAttack", ampAttack);
		json.put("ampDecay", ampDecay);
		json.put("ampSustain", ampSustain);
		json.put("ampRelease", ampRelease);
		json.put("ampOverdrive", ampOverdrive);
		json.put("ampOverdrivePerVoice", ampOverdrivePerVoice);
		json.put("ampVolume", ampVolume);
		
		// Vibrato
		json.put("vibratoDestination", vibratoDestination);
		json.put("vibratoType", vibratoType);
		json.put("vibratoAmount", vibratoAmount);
		json.put("vibratoRate", vibratoRate);
		json.put("vibratoSync", vibratoSync);
		json.put("vibratoEnvelopeSet", vibratoEnvelopeSet);
		json.put("vibratoAttack", vibratoAttack);
		json.put("vibratoDecay", vibratoDecay);
		
		// Vibrato 2
		json.put("vibrato2Destination", vibrato2Destination);
		json.put("vibrato2Type", vibrato2Type);
		json.put("vibrato2Amount", vibrato2Amount);
		json.put("vibrato2Rate", vibrato2Rate);
		json.put("vibrato2Sync", vibrato2Sync);
		json.put("vibrato2EnvelopeSet", vibrato2EnvelopeSet);
		json.put("vibrato2Attack", vibrato2Attack);
		json.put("vibrato2Decay", vibrato2Decay);
		
		// Effects
		json.put("echoAmount", echoAmount);
		json.put("echoDelay", echoDelay);
		json.put("echoFeedback", echoFeedback);
		json.put("flangeAmount", flangeAmount);
		json.put("flangeRate", flangeRate);
		
		// Harsh Noise Effects
		json.put("distortionAmount", distortionAmount);
		json.put("bitCrushAmount", bitCrushAmount);
		json.put("whiteNoiseAmount", whiteNoiseAmount);
		json.put("pinkNoiseAmount", pinkNoiseAmount);
		json.put("brownNoiseAmount", brownNoiseAmount);
		json.put("staticNoiseAmount", staticNoiseAmount);
		json.put("reverbAmount", reverbAmount);
		json.put("limiterAmount", limiterAmount);
		json.put("compressorAmount", compressorAmount);
		
		// Sequence
		JSONArray sequenceArray = new JSONArray();
		if (sequence != null) {
			for (int s : sequence) {
				sequenceArray.put(s);
			}
		}
		json.put("sequence", sequenceArray);
		
		// Sequence velocity
		JSONArray sequenceVelocityArray = new JSONArray();
		if (sequenceVelocity != null) {
			for (int v : sequenceVelocity) {
				sequenceVelocityArray.put(v);
			}
		}
		json.put("sequenceVelocity", sequenceVelocityArray);
		
		// Sequence mute
		JSONArray sequenceMuteArray = new JSONArray();
		if (sequenceMute != null) {
			for (boolean m : sequenceMute) {
				sequenceMuteArray.put(m);
			}
		}
		json.put("sequenceMute", sequenceMuteArray);
		
		json.put("sequenceRate", sequenceRate);
		json.put("sequenceLoop", sequenceLoop);
		json.put("bpmSequenceRate", bpmSequenceRate);
		
		// Metadata
		if (author != null && !author.isEmpty()) {
			json.put("author", author);
		}
		
		json.put("zeroAdjustedEnvelopes", zeroAdjustedEnvelopes);
		
		return json.toString(2); // Pretty print with 2 space indent
	}
}

