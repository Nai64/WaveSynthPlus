package com.gallantrealm.easysynth;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.gallantrealm.mysynth.AbstractInstrument;
import com.gallantrealm.mysynth.MySynth;
import com.gallantrealm.mysynth.MySynthMidi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WaveSynth extends AbstractInstrument {

	private static final int K32 = 32768;
	private static final int K16 = 16384;
	private static final int K8 = 8192;


	public static final int MODE_MONOPHONIC = 0;
	public static final int MODE_POLYPHONIC = 1;
	public static final int MODE_CHORUS = 2;
	protected int mode;
	protected int octave;
	protected int key;
	protected float chorusWidth = 0.1f;
	protected boolean hold;

	public static final int TUNING_EQUAL_TEMPERAMENT = 0;
	public static final int TUNING_JUST_INTONATION = 1;
	public static final int TUNING_BAD = 2;
	protected int tuning = 0;

	protected int tuningCents = 0;

	protected float[] harmonics = new float[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	protected float ampVolume = 0.5f;
	protected float ampAttack = 0.0f;
	protected float ampDecay = 1000.0f;
	protected float ampSustain = 0.0f;
	protected float ampRelease = 0.0f;
	protected int ampOverdrive = 0;
	protected boolean ampOverdrivePerVoice;

	public static final int FILTER_LOWPASS = 0;
	public static final int FILTER_BANDPASS = 1;
	public static final int FILTER_FADE = 2;
	public static final int FILTER_HIGHPASS = 3;
	public static final int FILTER_COMB1 = 4;
	public static final int FILTER_COMB2 = 5;
	public static final int FILTER_COMB3 = 6;
	public static final int FILTER_COMB4 = 7;
	public static final int FILTER_FORMANT1 = 8;
	public static final int FILTER_FORMANT2 = 9;
	public static final int FILTER_FORMANT3 = 10;
	public static final int FILTER_FORMANT4 = 11;
	protected int filterType = FILTER_LOWPASS;
	protected float filterResonance = 0f;
	protected float filterLow = 0f;
	protected float filterHigh = 1.0f;
	protected float filterEnvAttack = 0.0f;
	protected float filterEnvDecay = 100.0f;
	protected float filterEnvSustain = 0f;
	protected float filterEnvRelease = 0.0f;
	protected boolean filterFixed = false;

	public static final int VIBRATO_DESTINATION_PITCH = 0;
	public static final int VIBRATO_DESTINATION_FILTER = 1;
	public static final int VIBRATO_DESTINATION_AMP = 2;
	public static final int VIBRATO_DESTINATION_MOD_PITCH = 3;
	public static final int VIBRATO_DESTINATION_MOD_AMP = 4;
	protected int vibratoDestination;
	public static final int VIBRATO_TYPE_WAVE = 0;
	public static final int VIBRATO_TYPE_RAMP_UP = 1;
	public static final int VIBRATO_TYPE_RAMP_DOWN = 2;
	public static final int VIBRATO_TYPE_TWO_TONE = 3;
	public static final int VIBRATO_TYPE_NOISE = 4;
	public static final int VIBRATO_TYPE_PULSE4 = 5;
	public static final int VIBRATO_TYPE_PULSE8 = 6;
	public static final int VIBRATO_TYPE_PULSE16 = 7;
	protected int vibratoType = VIBRATO_TYPE_WAVE;
	protected float vibratoRate;
	protected float vibratoAmount;
	protected float vibratoAttack;
	protected float vibratoDecay;
	protected boolean vibratoSync;
	protected int vibrato2Destination;
	protected int vibrato2Type = VIBRATO_TYPE_WAVE;
	protected float vibrato2Rate;
	protected float vibrato2Amount;
	protected float vibrato2Attack;
	protected float vibrato2Decay;
	protected boolean vibrato2Sync;

	protected float portamentoAmount;

	protected float echoAmount;
	protected float echoDelay;
	protected float echoFeedback;

	protected float flangeRate;
	protected float flangeAmount;

	protected float distortionAmount;
	protected float bitCrushAmount;
	protected float whiteNoiseAmount;
	protected float pinkNoiseAmount;
	protected float brownNoiseAmount;
	protected float staticNoiseAmount;

	protected float reverbAmount;
	protected float limiterAmount;
	protected float compressorAmount;
	
	// Advanced effects parameters
	protected float reverbRoomSize = 0.5f;
	protected float reverbDamping = 0.5f;
	protected float limiterThreshold = 0.8f;
	protected float limiterRelease = 0.5f;
	protected float compressorRatio = 0.5f;
	protected float compressorAttack = 0.5f;

	// Visualizer support
	private short[] lastAudioBuffer;
	private static final int VISUALIZER_BUFFER_SIZE = 512;
	private int visualizerBufferIndex = 0;

	public static final int SEQUENCE_LENGTH = 16;
	protected int[] sequence = new int[SEQUENCE_LENGTH];
	protected float sequenceRate;
	protected boolean sequenceLoop;
	protected int sequencerBaseNote;

	public static final int EXPRESSION_NONE = 0;
	public static final int EXPRESSION_VOLUME = 1;
	public static final int EXPRESSION_FILTER = 2;
	public static final int EXPRESSION_VIBRATO = 3;
	public static final int EXPRESSION_PITCH = 4;
	public static final int EXPRESSION_VOLUME_AND_FILTER = 5;
	protected int expressionType = EXPRESSION_NONE;

	public static final int VELOCITY_NONE = 0;
	public static final int VELOCITY_VOLUME = 1;
	public static final int VELOCITY_FILTER = 2;
	public static final int VELOCITY_BOTH = 3;
	protected int velocityType = VELOCITY_NONE;


	private int sampleRate;
	private int envelopeSampleRate;
	private float waveSizeDivSampleRate;

	public WaveSynth(Context context) {
		sampleRate = AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_MUSIC); // usually 44100
		envelopeSampleRate = sampleRate / ENVELOPE_RATE;
		waveSizeDivSampleRate = (float) WAVE_SIZE / (float)sampleRate;

		System.out.println("EasySynth: sampleRate="+sampleRate);
	}

	/**
	 * This may or may not be needed, but is called after settings have changed
	 */
	public void updateParams() {
		needToGenerateWaves = true;
	}

	/** Used to tell the synth that sequence has changed.  This may not be needed anymore. */
	public void updateSequence() {

	}

	boolean needToGenerateWaves;
	boolean waveGeneratorRunning;
	private static final int TABLE_SIZE = 256; // number of waves
	private static final int WAVE_SIZE = 4096; // number of samples in each wave
	private static final int WAVE_MASK = 0xfff; // one less than wave size
	private int[][] waveTable = new int[TABLE_SIZE][WAVE_SIZE];

	/**
	 * Update waves based on harmonic selection and filtering
	 */
	public void genWaves() {
		needToGenerateWaves = true;
		if (!waveGeneratorRunning) {
			Thread waveGenerationThread = new Thread() {

				public void run() {
					System.out.println("EasySynth.genWaves -- generating");
					while (needToGenerateWaves) {
						needToGenerateWaves = false;

						// do a quick pass to build a rough table
//						int[][] newWaveTable = waveTable; // new int[TABLE_SIZE][WAVE_SIZE];
//						for (int t = 0; t < TABLE_SIZE; t += 16) {
//							genWave(t, newWaveTable);
//							for (int u = t + 1; u < t + 16; u++) {
//								for (int i = 0; i < WAVE_SIZE; i++) {
//									newWaveTable[u][i] = newWaveTable[t][i];
//								}
//							}
//						}
//						normalize(newWaveTable);
//						waveTable = newWaveTable;

						// build a more detailed table for the best sound
						int[][] newWaveTable = new int[TABLE_SIZE][WAVE_SIZE];
						for (int t = 0; t < TABLE_SIZE && !needToGenerateWaves; t++) {
							genWave(t, newWaveTable);
						} // for t in TABLE_SIZE
						if (!needToGenerateWaves) {
							normalize(newWaveTable);
							waveTable = newWaveTable;
						}
					}
					System.out.println("EasySynth.genWaves -- done");
					waveGeneratorRunning = false;
				}

				public void genWave(int t, int[][] newWaveTable) {
					for (int h = 0; h < harmonics.length; h++) {
						if (harmonics[h] != 0) {
							float hVol = 1;
							if (filterType == FILTER_LOWPASS) {
								float q = t / (float) TABLE_SIZE;
								float offset = q * q - h / (float) harmonics.length;
								if (offset > 0) {
									offset = 0.5f * filterResonance * filterResonance * offset;
								}
								hVol = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset) * filterResonance * 2.0f, 0, 1));
							} else if (filterType == FILTER_BANDPASS) {
								float q = t / (float) TABLE_SIZE;
								float offset = q * q - h / (float) harmonics.length;
								if (FastMath.abs(offset) < 0.1f) {
									offset = 0;
								}
								hVol = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset) * filterResonance * 2.0f, 0, 1));
							} else if (filterType == FILTER_FADE) {
								hVol = harmonics[h] * (1 - FastMath.range(h * (TABLE_SIZE - t) / (float) TABLE_SIZE / harmonics.length * filterResonance * 8.0f, 0, 1));
							} else if (filterType == FILTER_HIGHPASS) {
								float q = t / (float) TABLE_SIZE;
								float offset = q * q - h / (float) harmonics.length;
								if (offset < 0) {
									offset = 0.5f * filterResonance * filterResonance * offset;
								}
								hVol = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset) * filterResonance * 2.0f, 0, 1));
							} else if (filterType == FILTER_COMB1) {
								hVol = harmonics[h] * FastMath.range(0.5f + 4 * filterResonance * FastMath.cos(2.0f * FastMath.PI * ((float) h / harmonics.length + (float) t / TABLE_SIZE / 2.0f)), 0, 1);
							} else if (filterType == FILTER_COMB2) {
								hVol = harmonics[h] * FastMath.range(0.5f + 4 * filterResonance * FastMath.cos(4.0f * FastMath.PI * ((float) h / harmonics.length + (float) t / TABLE_SIZE / 4.0f)), 0, 1);
							} else if (filterType == FILTER_COMB3) {
								hVol = harmonics[h] * FastMath.range(0.5f + 4 * filterResonance * FastMath.cos(6.0f * FastMath.PI * ((float) h / harmonics.length + (float) t / TABLE_SIZE / 6.0f)), 0, 1);
							} else if (filterType == FILTER_COMB4) {
								hVol = harmonics[h] * FastMath.range(0.5f + 4 * filterResonance * FastMath.cos(8.0f * FastMath.PI * ((float) h / harmonics.length + (float) t / TABLE_SIZE / 8.0f)), 0, 1);
							} else if (filterType == FILTER_FORMANT1) {
								float offset1 = 0.0f + 0.1f * t / TABLE_SIZE - h / (float) harmonics.length;
								float hVol1 = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset1) * filterResonance * 16.0f, 0, 1));
								float offset2 = 0.3f - 0.2f * t / TABLE_SIZE - h / (float) harmonics.length;
								float hVol2 = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset2) * filterResonance * 8.0f, 0, 1));
								float offset3 = 0.5f - 0.3f * t / TABLE_SIZE - h / (float) harmonics.length;
								float hVol3 = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset3) * filterResonance * 4.0f, 0, 1));
								hVol = FastMath.max(hVol1, hVol2);
								hVol = FastMath.max(hVol, hVol3);
							} else if (filterType == FILTER_FORMANT2) {
								float offset1 = 0.01f + 0.05f * t / TABLE_SIZE - h / (float) harmonics.length;
								float hVol1 = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset1) * filterResonance * 16.0f, 0, 1));
								float offset2 = 0.5f - 0.4f * t / TABLE_SIZE - h / (float) harmonics.length;
								float hVol2 = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset2) * filterResonance * 8.0f, 0, 1));
								// float offset3 = 0.6f - 0.4f * t / TABLE_SIZE - h / (float) harmonics.length;
								// float hVol3 = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset3) * filterResonance * 6.0f, 0, 1));
								hVol = FastMath.max(hVol1, hVol2);
								// hVol = FastMath.max(hVol, hVol3);
							} else if (filterType == FILTER_FORMANT3) {
								float offset1 = 0.0f + 0.15f * t / TABLE_SIZE - h / (float) harmonics.length;
								float hVol1 = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset1) * filterResonance * 16.0f, 0, 1));
								float offset2 = 0.05f + 0.2f * t / TABLE_SIZE - h / (float) harmonics.length;
								float hVol2 = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset2) * filterResonance * 8.0f, 0, 1));
								float offset3 = 0.40f - 0.1f * t / TABLE_SIZE - h / (float) harmonics.length;
								float hVol3 = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset3) * filterResonance * 6.0f, 0, 1));
								hVol = FastMath.max(hVol1, hVol2);
								hVol = FastMath.max(hVol, hVol3);
							} else if (filterType == FILTER_FORMANT4) {
								float offset1 = 0.2f - 0.15f * t / TABLE_SIZE - h / (float) harmonics.length;
								float hVol1 = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset1) * filterResonance * 16.0f, 0, 1));
								float offset2 = 0.3f - 0.15f * t / TABLE_SIZE - h / (float) harmonics.length;
								float hVol2 = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset2) * filterResonance * 8.0f, 0, 1));
								float offset3 = 0.31f + 0.1f * t / TABLE_SIZE - h / (float) harmonics.length;
								float hVol3 = harmonics[h] * (1 - FastMath.range(FastMath.abs(4 * offset3) * filterResonance * 6.0f, 0, 1));
								hVol = FastMath.max(hVol1, hVol2);
								hVol = FastMath.max(hVol, hVol3);
							}
							hVol *= 0.9f; // avoids clipping
							// float q = 1.0f / WAVE_SIZE * FastMath.PI * 2 * (h + 1);
							float q = 1.0f / WAVE_SIZE * FastMath.PI * 2 * (h + 1) * FastMath.radToIndex;
							int[] nwt = newWaveTable[t];
							for (int i = 0; i < WAVE_SIZE; i++) {
								// newWaveTable[t][i] += (int) (hVol * FastMath.sin(q * i) * K32);
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
								i++;
								nwt[i] += (int) (hVol * FastMath.sin[(int) (q * i) & FastMath.SIN_MASK] * K32);
							}
						}
					}
				}

				public void normalize(int[][] newWaveTable) {
					// normalize and divide by five (to account for polyphonic/chorus volumes and some echo feedback)
					int maxmag = 1;
					for (int t = 0; t < TABLE_SIZE; t++) {
						int[] nwt = newWaveTable[t];
						for (int i = 0; i < WAVE_SIZE; i++) {
							int mag = Math.abs(nwt[i]);
							if (mag > maxmag) {
								maxmag = mag;
							}
						}
					}
					float multiplier = 0.75f / ((float) maxmag / K32); // 0.75 avoids clipping
					for (int t = 0; t < TABLE_SIZE; t++) {
						int[] nwt = newWaveTable[t];
						for (int i = 0; i < WAVE_SIZE; i++) {
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
							i++;
							nwt[i] = (int) (nwt[i] * multiplier);
						}
					}
				}

			};
			waveGenerationThread.start();
			waveGeneratorRunning = true;
		}
	}

	private static final int VIBRATO_WAVE_SIZE = WAVE_SIZE;
	private int[] vibratoTable = new int[VIBRATO_WAVE_SIZE];
	private int[] vibrato2Table = new int[VIBRATO_WAVE_SIZE];

	/**
	 * Update vibrato waves based on settings.
	 */
	public void genVibratoWaves() {
		System.out.println("EasySynth.genVibratoWaves");
		vibratoTable = genVibratoWave(vibratoType);
		vibrato2Table = genVibratoWave(vibrato2Type);
//		nativeGenVibratoWave(vibratoTable, vibrato2Table);
	}

	private int[] genVibratoWave(int vibratoType) {
		int[] newVibratoTable = new int[VIBRATO_WAVE_SIZE];
		for (int i = 0; i < VIBRATO_WAVE_SIZE; i++) {
			if (vibratoType == VIBRATO_TYPE_WAVE) {
				newVibratoTable[i] = (int) (FastMath.sin((float) i / VIBRATO_WAVE_SIZE * FastMath.PI * 2) * K32);
			} else if (vibratoType == VIBRATO_TYPE_RAMP_UP) {
				newVibratoTable[i] = (int) ((2.0f * i / VIBRATO_WAVE_SIZE - 1.0f) * K32);
			} else if (vibratoType == VIBRATO_TYPE_RAMP_DOWN) {
				newVibratoTable[i] = (int) ((1.0f - 2.0f * i / VIBRATO_WAVE_SIZE) * K32);
			} else if (vibratoType == VIBRATO_TYPE_TWO_TONE) {
				if (i < VIBRATO_WAVE_SIZE / 2) {
					newVibratoTable[i] = (1 * K32);
				} else {
					newVibratoTable[i] = (-1 * K32);
				}
			} else if (vibratoType == VIBRATO_TYPE_NOISE) {
				newVibratoTable[i] = (int) (FastMath.random(-1, 1) * K32);
			} else if (vibratoType == VIBRATO_TYPE_PULSE4) {
				if (i < VIBRATO_WAVE_SIZE / 4) {
					if (i < VIBRATO_WAVE_SIZE / 8) {
						newVibratoTable[i] = (1 * K32);
					} else {

					}
				} else {
					newVibratoTable[i] = (-1 * K32);
				}
			} else if (vibratoType == VIBRATO_TYPE_PULSE8) {
				if (i < VIBRATO_WAVE_SIZE / 8) {
					newVibratoTable[i] = (1 * K32);
				} else {
					newVibratoTable[i] = (-1 * K32);
				}
			} else if (vibratoType == VIBRATO_TYPE_PULSE16) {
				if (i < VIBRATO_WAVE_SIZE / 16) {
					newVibratoTable[i] = (1 * K32);
				} else {
					newVibratoTable[i] = (-1 * K32);
				}
			}
		}
		return newVibratoTable;
	}



	int lastNote1, lastNote2, lastNote3, lastNote4;

	public void keyPress(int voice, int note, float velocity, boolean glide, boolean startsSilent) {
		if (sequenceRate > 0) {
			sequencerBaseNote = note;
			if (!sequencerRunning) {
				sequencerRunning = true;
				sequenceIndex = 0;
				sequenceNote = sequence[(int) sequenceIndex];
				sequenceIndex = SEQUENCE_LENGTH - 0.01f; // to force immediate play
			}
		} else {
			if (mode == MODE_MONOPHONIC) {
				releaseKey1 = false;
				internalKeyPress(1, note, velocity, glide, startsSilent, 0);
			} else if (mode == MODE_CHORUS) {
				releaseKey1 = false;
				releaseKey2 = false;
				releaseKey3 = false;
				releaseKey4 = false;
				internalKeyPress(1, note, velocity, glide, startsSilent, -0.07f * chorusWidth);
				internalKeyPress(2, note, velocity, glide, startsSilent, -0.03f * chorusWidth);
				internalKeyPress(3, note, velocity, glide, startsSilent, +0.05f * chorusWidth);
				internalKeyPress(4, note, velocity, glide, startsSilent, +0.11f * chorusWidth);
			} else {
				if (voice == 1) {
					releaseKey1 = false;
				} else if (voice == 2) {
					releaseKey2 = false;
				} else if (voice == 3) {
					releaseKey3 = false;
				} else if (voice == 4) {
					releaseKey4 = false;
				}
				internalKeyPress(voice, note, velocity, glide, startsSilent, 0);
			}
		}
	}

	public int getVoice(int note) {
		return internalGetVoice(note);
	}

	public void keyRelease(int voice) {
		if (sequenceRate > 0) {
			sequencerRunning = false;
			internalKeyRelease(1);
			internalKeyRelease(2);
			internalKeyRelease(3);
			internalKeyRelease(4);
		} else {
			if (mode == MODE_CHORUS) {
				internalKeyRelease(1);
				internalKeyRelease(2);
				internalKeyRelease(3);
				internalKeyRelease(4);
			} else {
				internalKeyRelease(voice);
			}
		}
	}

	public void allSoundOff() {
		internalKeyRelease(1);
		internalKeyRelease(2);
		internalKeyRelease(3);
		internalKeyRelease(4);
		amplitude1 = 0;
		amplitude2 = 0;
		amplitude3 = 0;
		amplitude4 = 0;
	}

	/// Loading and saving sound (presets)

	public Sound generateSound() {
		Sound sound = new Sound();
		sound.zeroAdjustedEnvelopes = true;
		sound.mode = mode;
		sound.hold = hold;
		sound.chorusWidthSet = true;
		sound.chorusWidth = chorusWidth;
		sound.octave = octave;
		sound.key = key;
		sound.portamentoAmount = portamentoAmount;
		sound.velocityType = velocityType;
		sound.expressionType = expressionType;
		sound.harmonics = harmonics;
		sound.filterType = filterType;
		sound.filterResonance = filterResonance;
		sound.filterCutoff = filterLow;
		sound.filterEnvAmount = filterHigh;
		sound.filterEnvAttack = filterEnvAttack;
		sound.filterEnvDecay = filterEnvDecay;
		sound.filterEnvSustain = filterEnvSustain;
		sound.filterEnvRelease = filterEnvRelease;
		sound.ampVolume = ampVolume;
		sound.ampAttack = ampAttack;
		sound.ampDecay = ampDecay;
		sound.ampSustain = ampSustain;
		sound.ampRelease = ampRelease;
		sound.ampOverdrive = ampOverdrive;
		sound.ampOverdrivePerVoice = ampOverdrivePerVoice;
		sound.vibratoDestination = vibratoDestination;
		sound.vibratoType = vibratoType;
		sound.vibratoAmount = vibratoAmount;
		sound.vibratoRate = vibratoRate;
		sound.vibratoSync = vibratoSync;
		sound.vibratoEnvelopeSet = true;
		sound.vibratoAttack = vibratoAttack;
		sound.vibratoDecay = vibratoDecay;
		sound.vibrato2Destination = vibrato2Destination;
		sound.vibrato2Type = vibrato2Type;
		sound.vibrato2Amount = vibrato2Amount;
		sound.vibrato2Rate = vibrato2Rate;
		sound.vibrato2Sync = vibrato2Sync;
		sound.vibrato2EnvelopeSet = true;
		sound.vibrato2Attack = vibrato2Attack;
		sound.vibrato2Decay = vibrato2Decay;
		sound.echoAmount = echoAmount;
		sound.echoDelay = echoDelay;
		sound.echoFeedback = echoFeedback;
		sound.flangeAmount = flangeAmount;
		sound.flangeRate = flangeRate;
		sound.distortionAmount = distortionAmount;
		sound.bitCrushAmount = bitCrushAmount;
		sound.whiteNoiseAmount = whiteNoiseAmount;
		sound.pinkNoiseAmount = pinkNoiseAmount;
		sound.brownNoiseAmount = brownNoiseAmount;
		sound.staticNoiseAmount = staticNoiseAmount;
		sound.reverbAmount = reverbAmount;
		sound.limiterAmount = limiterAmount;
		sound.compressorAmount = compressorAmount;
		sound.sequence = sequence;
		sound.sequenceRate = sequenceRate;
		sound.bpmSequenceRate = true;
		sound.sequenceLoop = sequenceLoop;
		return sound;
	}

	public void applySound(Sound sound) {
		if (sound.mode == 0) {
			mode = MODE_MONOPHONIC;
		} else if (sound.mode == 1) {
			mode = MODE_POLYPHONIC;
		} else if (sound.mode == 2) {
			mode = MODE_CHORUS;
		}
		if (sound.chorusWidthSet) {
			chorusWidth = sound.chorusWidth;
		} else {
			chorusWidth = 0.1f;
		}
		octave = sound.octave;
		key = sound.key;
		portamentoAmount = sound.portamentoAmount;
		hold = sound.hold;
		velocityType = sound.velocityType;
		expressionType = sound.expressionType;
		harmonics = sound.harmonics;
		filterType = sound.filterType;
		filterResonance = sound.filterResonance;
		filterLow = sound.filterCutoff;
		filterHigh = sound.filterEnvAmount;
		if (sound.zeroAdjustedEnvelopes) {
			filterEnvAttack = sound.filterEnvAttack;
			filterEnvDecay = sound.filterEnvDecay;
			filterEnvSustain = sound.filterEnvSustain;
			filterEnvRelease = sound.filterEnvRelease;
			if (sound.ampVolume == 0) {
				ampVolume = 0.5f;
			} else {
				ampVolume = sound.ampVolume;
			}
			ampAttack = sound.ampAttack;
			ampDecay = sound.ampDecay;
			ampSustain = sound.ampSustain;
			ampRelease = sound.ampRelease;
			ampOverdrive = sound.ampOverdrive;
			ampOverdrivePerVoice = sound.ampOverdrivePerVoice;
			vibratoDestination = sound.vibratoDestination;
			vibratoType = sound.vibratoType;
			vibratoAmount = sound.vibratoAmount;
			vibratoRate = sound.vibratoRate;
			vibratoSync = sound.vibratoSync;
			vibratoAttack = sound.vibratoAttack;
			vibratoDecay = sound.vibratoDecay;
			vibrato2Destination = sound.vibrato2Destination;
			vibrato2Type = sound.vibrato2Type;
			vibrato2Amount = sound.vibrato2Amount;
			vibrato2Rate = sound.vibrato2Rate;
			vibrato2Sync = sound.vibrato2Sync;
			vibrato2Attack = sound.vibrato2Attack;
			vibrato2Decay = sound.vibrato2Decay;
		} else {
			filterEnvAttack = Math.max(0, sound.filterEnvAttack - 1);
			filterEnvDecay = Math.max(0, sound.filterEnvDecay - 1);
			filterEnvSustain = sound.filterEnvSustain;
			filterEnvRelease = Math.max(0, sound.filterEnvRelease - 1);
			if (sound.ampVolume == 0) {
				ampVolume = 0.5f;
			} else {
				ampVolume = sound.ampVolume;
			}
			ampAttack = Math.max(0, sound.ampAttack - 1);
			ampDecay = Math.max(0, sound.ampDecay - 1);
			ampSustain = sound.ampSustain;
			ampRelease = Math.max(0, sound.ampRelease - 1);
			ampOverdrive = sound.ampOverdrive;
			ampOverdrivePerVoice = sound.ampOverdrivePerVoice;
			vibratoType = sound.vibratoType;
			vibratoAmount = sound.vibratoAmount;
			vibratoRate = sound.vibratoRate;
			vibratoSync = sound.vibratoSync;
			if (sound.vibratoEnvelopeSet) {
				vibratoAttack = Math.max(0, sound.vibratoAttack - 1);
				vibratoDecay = Math.max(0, sound.vibratoDecay - 1);
			} else {
				vibratoAttack = 0;
				vibratoDecay = 1000;
			}
			vibrato2Type = sound.vibrato2Type;
			vibrato2Amount = sound.vibrato2Amount;
			vibrato2Rate = sound.vibrato2Rate;
			vibrato2Sync = sound.vibrato2Sync;
			if (sound.vibrato2EnvelopeSet) {
				vibrato2Attack = Math.max(0, sound.vibrato2Attack - 1);
				vibrato2Decay = Math.max(0, sound.vibrato2Decay - 1);
			} else {
				vibrato2Attack = 0;
				vibrato2Decay = 1000;
			}
		}
		echoAmount = sound.echoAmount;
		echoDelay = sound.echoDelay;
		echoFeedback = sound.echoFeedback;
		flangeAmount = sound.flangeAmount;
		flangeRate = sound.flangeRate;
		distortionAmount = sound.distortionAmount;
		bitCrushAmount = sound.bitCrushAmount;
		whiteNoiseAmount = sound.whiteNoiseAmount;
		pinkNoiseAmount = sound.pinkNoiseAmount;
		brownNoiseAmount = sound.brownNoiseAmount;
		staticNoiseAmount = sound.staticNoiseAmount;
		reverbAmount = sound.reverbAmount;
		limiterAmount = sound.limiterAmount;
		compressorAmount = sound.compressorAmount;
		sequence = sound.sequence;
		sequenceRate = sound.sequenceRate;
		if (!sound.bpmSequenceRate) {
			sequenceRate *= 6;
		}
		sequenceLoop = sound.sequenceLoop;
		genVibratoWaves();
		updateParams();
		updateSequence();
		genWaves();
	}


	/// Methods to implement AbstractInstrument


	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public void clearDirty() {

	}

	@Override
	public boolean isEditing() {
		return false;
	}

	@Override
	public void setEditing(boolean edit) {

	}

	@Override
	public void initialize(int sampleRate) {

	}

	@Override
	public void terminate() {

	}

	@Override
	public boolean isSounding() {
		return true;
	}

	@Override
	public void setSustaining(boolean sustaining) {

	}

	@Override
	public boolean isSustaining() {
		return false;
	}

	@Override
	public void notePress(int midinote, float midivelocity) {
		int note = midinote - 60 + 12;
		float velocity = FastMath.min(1.0f, (midivelocity + 1) / 64.0f);
		if (mode == MODE_MONOPHONIC) {
			keyPress(1, note, velocity, false, true);
			lastNote1 = note;
		} else if (mode == MODE_CHORUS) {
			keyPress(1, note, velocity, false, true);
			lastNote1 = note;
			lastNote2 = note;
			lastNote3 = note;
			lastNote4 = note;
		} else if (mode == MODE_POLYPHONIC) {
			int voice = getVoice(note);
			keyPress(voice, note, velocity, false, true);
			if (voice == 1) {
				lastNote1 = note;
			} else if (voice == 2) {
				lastNote2 = note;
			} else if (voice == 3) {
				lastNote3 = note;
			} else if (voice == 4) {
				lastNote4 = note;
			}
		}
	}

	@Override
	public void noteRelease(int midinote) {
		int note = midinote - 60 + 12;
		if (mode == MODE_MONOPHONIC) {
			if (lastNote1 == note) {
				keyRelease(1);
			}
		} else if (mode == MODE_CHORUS) {
			if (lastNote1 == note) {
				keyRelease(1);
				keyRelease(2);
				keyRelease(3);
				keyRelease(4);
			}
		} else if (mode == MODE_POLYPHONIC) {
			if (lastNote1 == note) {
				keyRelease(1);
			} else if (lastNote2 == note) {
				keyRelease(2);
			} else if (lastNote3 == note) {
				keyRelease(3);
			} else if (lastNote4 == note) {
				keyRelease(4);
			}
		}
	}

	@Override
	public void pitchBend(float bend) {
		internalPitchBend(bend);
	}

	@Override
	public void expression(float amount) {
		if (expressionType == EXPRESSION_VOLUME) {
			volumeExpression = amount;
			filterExpression = 0;
			vibratoExpression = 0;
			vibrato2Expression = 0;
			pitchExpression = 0;
		} else if (expressionType == EXPRESSION_FILTER) {
			filterExpression = amount;
			volumeExpression = 1;
			vibratoExpression = 0;
			vibrato2Expression = 0;
			pitchExpression = 0;
		} else if (expressionType == EXPRESSION_VIBRATO) {
			vibratoExpression = amount / 4.0f;
			vibrato2Expression = 0;
			volumeExpression = 1;
			filterExpression = 0;
			pitchExpression = 0;
		} else if (expressionType == EXPRESSION_PITCH) {
			volumeExpression = 1;
			filterExpression = 0;
			vibratoExpression = 0;
			vibrato2Expression = 0;
			pitchExpression = amount;
		} else if (expressionType == EXPRESSION_VOLUME_AND_FILTER) {
			volumeExpression = amount;
			filterExpression = amount;
			vibratoExpression = 0;
			vibrato2Expression = 0;
			pitchExpression = 0;
		} else {
			volumeExpression = 1;
			filterExpression = 0;
			vibratoExpression = 0;
			vibrato2Expression = 0;
			pitchExpression = 0;
		}
		if (bend != pitchExpression) {
			internalPitchBend(pitchExpression);
		}
		if (firstExpression) {
			sVolumeExpression = volumeExpression;
			sFilterExpression = filterExpression;
			firstExpression = false;
		}
	}

	@Override
	public void pressure(float amount) {

	}

	@Override
	public void controlChange(int control, double value) {

	}

	@Override
	public void midiclock() {

	}

	public static final int MAX_SAMPLE_RATE = 50000;
	public static final int ENVELOPE_RATE = 100;

	private int sequenceNote;
	private float sequenceIndex;
	private boolean sequencerRunning;

	private float[] echoTable = new float[MAX_SAMPLE_RATE]; // 1 sec of echo

	private boolean keyDown1;
	private boolean releaseKey1;
	private int note1;
	private float noteFrequency1;
	private float velocity1;
	private float filterVelocity1;
	private float detune1;
	private boolean ampAttacking1;
	private boolean filterAttacking1;
	private boolean vibratoAttacking1;
	private boolean vibrato2Attacking1;

	private boolean keyDown2;
	private boolean releaseKey2;
	private int note2;
	private float noteFrequency2;
	private float velocity2;
	private float filterVelocity2;
	private float detune2;
	private boolean ampAttacking2;
	private boolean filterAttacking2;
	private boolean vibratoAttacking2;
	private boolean vibrato2Attacking2;

	private boolean keyDown3;
	private boolean releaseKey3;
	private int note3;
	private float noteFrequency3;
	private float velocity3;
	private float filterVelocity3;
	private float detune3;
	private boolean ampAttacking3;
	private boolean filterAttacking3;
	private boolean vibratoAttacking3;
	private boolean vibrato2Attacking3;

	private boolean keyDown4;
	private boolean releaseKey4;
	private int note4;
	private float noteFrequency4;
	private float velocity4;
	private float filterVelocity4;
	private float detune4;
	private boolean ampAttacking4;
	private boolean filterAttacking4;
	private boolean vibratoAttacking4;
	private boolean vibrato2Attacking4;

	private float amplitude1;
	private float amplitude2;
	private float amplitude3;
	private float amplitude4;
	private float filterEnvAmplitude1;
	private float filterEnvAmplitude2;
	private float filterEnvAmplitude3;
	private float filterEnvAmplitude4;
	private float vibratoAmplitude1;
	private float vibratoAmplitude2;
	private float vibratoAmplitude3;
	private float vibratoAmplitude4;
	private float vibrato2Amplitude1;
	private float vibrato2Amplitude2;
	private float vibrato2Amplitude3;
	private float vibrato2Amplitude4;
	private boolean gliding1;
	private boolean gliding2;
	private boolean gliding3;
	private boolean gliding4;

	private int vph1 = 0;
	private int vph2 = 0;
	private int vph3 = 0;
	private int vph4 = 0;
	private int v2ph1 = 0;
	private int v2ph2 = 0;
	private int v2ph3 = 0;
	private int v2ph4 = 0;

	private static final float C0  = 16.352f;

	static float bend;
	static boolean firstExpression;

	static float volumeExpression;
	static float filterExpression;
	static float vibratoExpression;
	static float vibrato2Expression;
	static float pitchExpression;
	static float sVolumeExpression;
	static float sFilterExpression;
	static float sBend;

	private float frequency1 = 0.0f;
	private float frequency2 = 0.0f;
	private float frequency3 = 0.0f;
	private float frequency4 = 0.0f;
	private float amp1 = 0.0f;
	private float amp2 = 0.0f;
	private float amp3 = 0.0f;
	private float amp4 = 0.0f;
	private float filterAmplitude1 = 0.01f;
	private float filterAmplitude2 = 0.01f;
	private float filterAmplitude3 = 0.01f;
	private float filterAmplitude4 = 0.01f;

	private int fp_vibratoRate = 0;
	private int fp_vibrato2Rate = 0;
	private int ph1 = 0;
	private int ph2 = 0;
	private int ph3 = 0;
	private int ph4 = 0;
	private int fp_freq1 = 0;
	private int fp_freq2 = 0;
	private int fp_freq3 = 0;
	private int fp_freq4 = 0;
	private float smoothamp1 = 0.0f;
	private float smoothamp2 = 0.0f;
	private float smoothamp3 = 0.0f;
	private float smoothamp4 = 0.0f;
	private int fp_filterAmp1 = 0;
	private int fp_filterAmp2 = 0;
	private int fp_filterAmp3 = 0;
	private int fp_filterAmp4 = 0;
	private int fp_smoothFilterAmp1 = 0;
	private int fp_smoothFilterAmp2 = 0;
	private int fp_smoothFilterAmp3 = 0;
	private int fp_smoothFilterAmp4 = 0;
	private int fp_vibratoAmp1 = 0;
	private int fp_vibratoAmp2 = 0;
	private int fp_vibratoAmp3 = 0;
	private int fp_vibratoAmp4 = 0;
	private int fp_modVibratoAmp1 = 0;
	private int fp_modVibratoAmp2 = 0;
	private int fp_modVibratoAmp3 = 0;
	private int fp_modVibratoAmp4 = 0;
	private int fp_vibrato2Amp1 = 0;
	private int fp_vibrato2Amp2 = 0;
	private int fp_vibrato2Amp3 = 0;
	private int fp_vibrato2Amp4 = 0;

	private int fp_echoDelay = 0;
	private int echoIndex = 0;

	private int fp_flangeRate = 0;
	private int fp_flangeAmount = 0;
	private int flangeIndex = 0;

	private int[] flangeWave = new int[256];

	private int t;

	@Override
	public void generate(float[] output) {
		int i;
		t++;

		// vibrato2
		if (vibrato2Sync) {
			int r = fp_vibrato2Rate >> 12;
			v2ph1 += (fp_freq1 * r) >> 2;
			v2ph2 += (fp_freq2 * r) >> 2;
			v2ph3 += (fp_freq3 * r) >> 2;
			v2ph4 += (fp_freq4 * r) >> 2;
		} else {
			v2ph1 += fp_vibrato2Rate;
			v2ph2 += fp_vibrato2Rate;
			v2ph3 += fp_vibrato2Rate;
			v2ph4 += fp_vibrato2Rate;
		}

		// vibrato
		if (vibratoSync) {
			int r = fp_vibratoRate >> 12;
			vph1 += (fp_freq1 * r) >> 2;
			vph2 += (fp_freq2 * r) >> 2;
			vph3 += (fp_freq3 * r) >> 2;
			vph4 += (fp_freq4 * r) >> 2;
		} else {
			vph1 += fp_vibratoRate;
			vph2 += fp_vibratoRate;
			vph3 += fp_vibratoRate;
			vph4 += fp_vibratoRate;
		}
		if (vibrato2Destination == VIBRATO_DESTINATION_MOD_PITCH) {
			int fp_vibrato1 = (vibrato2Table[(v2ph1 >> 15) & WAVE_MASK] * fp_vibrato2Amp1) >> 15;
			int fp_vibrato2 = (vibrato2Table[(v2ph2 >> 15) & WAVE_MASK] * fp_vibrato2Amp2) >> 15;
			int fp_vibrato3 = (vibrato2Table[(v2ph3 >> 15) & WAVE_MASK] * fp_vibrato2Amp3) >> 15;
			int fp_vibrato4 = (vibrato2Table[(v2ph4 >> 15) & WAVE_MASK] * fp_vibrato2Amp4) >> 15;
			if (vibrato2Sync) {
//								vph1 += ((fp_vibrato1 * (note1 + 12 * octave + key)) << 5);
//								vph2 += ((fp_vibrato2 * (note2 + 12 * octave + key)) << 5);
//								vph3 += ((fp_vibrato3 * (note3 + 12 * octave + key)) << 5);
//								vph4 += ((fp_vibrato4 * (note4 + 12 * octave + key)) << 5);
				vph1 += ((fp_vibrato1 * (fp_freq1 >> 5)) >> 5);
				vph2 += ((fp_vibrato2 * (fp_freq2 >> 5)) >> 5);
				vph3 += ((fp_vibrato3 * (fp_freq3 >> 5)) >> 5);
				vph4 += ((fp_vibrato4 * (fp_freq4 >> 5)) >> 5);
			} else {
				vph1 += ((fp_vibrato1 * (fp_freq1 >> 5)) >> 10);
				vph2 += ((fp_vibrato2 * (fp_freq2 >> 5)) >> 10);
				vph3 += ((fp_vibrato3 * (fp_freq3 >> 5)) >> 10);
				vph4 += ((fp_vibrato4 * (fp_freq4 >> 5)) >> 10);
			}
		}
		if (vibrato2Destination == VIBRATO_DESTINATION_MOD_AMP) {
			int fp_vibrato1 = (vibrato2Table[(v2ph1 >> 15) & WAVE_MASK] * fp_vibrato2Amp1) >> 15;
			int fp_vibrato2 = (vibrato2Table[(v2ph2 >> 15) & WAVE_MASK] * fp_vibrato2Amp2) >> 15;
			int fp_vibrato3 = (vibrato2Table[(v2ph3 >> 15) & WAVE_MASK] * fp_vibrato2Amp3) >> 15;
			int fp_vibrato4 = (vibrato2Table[(v2ph4 >> 15) & WAVE_MASK] * fp_vibrato2Amp4) >> 15;
			fp_modVibratoAmp1 = (fp_vibratoAmp1 * fp_vibrato1) >> 15;
			fp_modVibratoAmp2 = (fp_vibratoAmp2 * fp_vibrato2) >> 15;
			fp_modVibratoAmp3 = (fp_vibratoAmp3 * fp_vibrato3) >> 15;
			fp_modVibratoAmp4 = (fp_vibratoAmp4 * fp_vibrato4) >> 15;
		} else {
			fp_modVibratoAmp1 = fp_vibratoAmp1;
			fp_modVibratoAmp2 = fp_vibratoAmp2;
			fp_modVibratoAmp3 = fp_vibratoAmp3;
			fp_modVibratoAmp4 = fp_vibratoAmp4;
		}

		// oscillators
		ph1 += fp_freq1;
		ph2 += fp_freq2;
		ph3 += fp_freq3;
		ph4 += fp_freq4;
		if (vibratoDestination == VIBRATO_DESTINATION_PITCH) {
			int fp_vibrato1 = (vibratoTable[(vph1 >> 15) & WAVE_MASK] * fp_modVibratoAmp1) >> 15;
			int fp_vibrato2 = (vibratoTable[(vph2 >> 15) & WAVE_MASK] * fp_modVibratoAmp2) >> 15;
			int fp_vibrato3 = (vibratoTable[(vph3 >> 15) & WAVE_MASK] * fp_modVibratoAmp3) >> 15;
			int fp_vibrato4 = (vibratoTable[(vph4 >> 15) & WAVE_MASK] * fp_modVibratoAmp4) >> 15;
			if (vibratoSync) {
				ph1 += ((fp_vibrato1 * (note1 + 12 * octave + key)) << 2);
				ph2 += ((fp_vibrato2 * (note2 + 12 * octave + key)) << 2);
				ph3 += ((fp_vibrato3 * (note3 + 12 * octave + key)) << 2);
				ph4 += ((fp_vibrato4 * (note4 + 12 * octave + key)) << 2);
			} else {
				ph1 += ((fp_vibrato1 * (fp_freq1 >> 5)) >> 10);
				ph2 += ((fp_vibrato2 * (fp_freq2 >> 5)) >> 10);
				ph3 += ((fp_vibrato3 * (fp_freq3 >> 5)) >> 10);
				ph4 += ((fp_vibrato4 * (fp_freq4 >> 5)) >> 10);
			}
		}
		if (vibrato2Destination == VIBRATO_DESTINATION_PITCH) {
			int fp_vibrato1 = (vibrato2Table[(v2ph1 >> 15) & WAVE_MASK] * fp_vibrato2Amp1) >> 15;
			int fp_vibrato2 = (vibrato2Table[(v2ph2 >> 15) & WAVE_MASK] * fp_vibrato2Amp2) >> 15;
			int fp_vibrato3 = (vibrato2Table[(v2ph3 >> 15) & WAVE_MASK] * fp_vibrato2Amp3) >> 15;
			int fp_vibrato4 = (vibrato2Table[(v2ph4 >> 15) & WAVE_MASK] * fp_vibrato2Amp4) >> 15;
			if (vibratoSync) {
				ph1 += ((fp_vibrato1 * (note1 + 12 * octave + key)) << 2);
				ph2 += ((fp_vibrato2 * (note2 + 12 * octave + key)) << 2);
				ph3 += ((fp_vibrato3 * (note3 + 12 * octave + key)) << 2);
				ph4 += ((fp_vibrato4 * (note4 + 12 * octave + key)) << 2);
			} else {
				ph1 += ((fp_vibrato1 * (fp_freq1 >> 5)) >> 10);
				ph2 += ((fp_vibrato2 * (fp_freq2 >> 5)) >> 10);
				ph3 += ((fp_vibrato3 * (fp_freq3 >> 5)) >> 10);
				ph4 += ((fp_vibrato4 * (fp_freq4 >> 5)) >> 10);
			}
		}

		// amps
		smoothamp1 = (63 * smoothamp1 + amp1) / 64;;
		smoothamp2 = (63 * smoothamp2 + amp2) / 64;;
		smoothamp3 = (63 * smoothamp3 + amp3) / 64;
		smoothamp4 = (63 * smoothamp4 + amp4) /64;
		if (vibratoDestination == VIBRATO_DESTINATION_AMP) {
			int fp_vibrato1 = K32 - (((-vibratoTable[(vph1 >> 15) & WAVE_MASK] + K32) * fp_modVibratoAmp1) >> 16);
			int fp_vibrato2 = K32 - (((-vibratoTable[(vph2 >> 15) & WAVE_MASK] + K32) * fp_modVibratoAmp2) >> 16);
			int fp_vibrato3 = K32 - (((-vibratoTable[(vph3 >> 15) & WAVE_MASK] + K32) * fp_modVibratoAmp3) >> 16);
			int fp_vibrato4 = K32 - (((-vibratoTable[(vph4 >> 15) & WAVE_MASK] + K32) * fp_modVibratoAmp4) >> 16);
			smoothamp1 *= fp_vibrato1 / 32768.0f;
			smoothamp2 *= fp_vibrato2 / 32768.0f;
			smoothamp3 *= fp_vibrato3 / 32768.0f;
			smoothamp4 *= fp_vibrato4 / 32768.0f;
		}
		if (vibrato2Destination == VIBRATO_DESTINATION_AMP) {
			int fp_vibrato1 = K32 - (((-vibrato2Table[(v2ph1 >> 15) & WAVE_MASK] + K32) * fp_vibrato2Amp1) >> 16);
			int fp_vibrato2 = K32 - (((-vibrato2Table[(v2ph2 >> 15) & WAVE_MASK] + K32) * fp_vibrato2Amp2) >> 16);
			int fp_vibrato3 = K32 - (((-vibrato2Table[(v2ph3 >> 15) & WAVE_MASK] + K32) * fp_vibrato2Amp3) >> 16);
			int fp_vibrato4 = K32 - (((-vibrato2Table[(v2ph4 >> 15) & WAVE_MASK] + K32) * fp_vibrato2Amp4) >> 16);
			smoothamp1 *= fp_vibrato1 / 32768.0f;
			smoothamp2 *= fp_vibrato2 / 32768.0f;
			smoothamp3 *= fp_vibrato3 / 32768.0f;
			smoothamp4 *= fp_vibrato4 / 32768.0f;
		}

		// filter amps
		fp_smoothFilterAmp1 = (63 * fp_smoothFilterAmp1 + fp_filterAmp1) >> 6;
		fp_smoothFilterAmp2 = (63 * fp_smoothFilterAmp2 + fp_filterAmp2) >> 6;
		fp_smoothFilterAmp3 = (63 * fp_smoothFilterAmp3 + fp_filterAmp3) >> 6;
		fp_smoothFilterAmp4 = (63 * fp_smoothFilterAmp4 + fp_filterAmp4) >> 6;
		if (vibratoDestination == VIBRATO_DESTINATION_FILTER) {
			int fp_vibrato1 = K32 - (((-vibratoTable[(vph1 >> 15) & WAVE_MASK] + K32) * fp_modVibratoAmp1) >> 15);
			fp_vibrato1 = (fp_vibrato1 < 0) ? -fp_vibrato1 : fp_vibrato1;
			int fp_vibrato2 = K32 - (((-vibratoTable[(vph2 >> 15) & WAVE_MASK] + K32) * fp_modVibratoAmp2) >> 15);
			fp_vibrato2 = (fp_vibrato2 < 0) ? -fp_vibrato2 : fp_vibrato2;
			int fp_vibrato3 = K32 - (((-vibratoTable[(vph3 >> 15) & WAVE_MASK] + K32) * fp_modVibratoAmp3) >> 15);
			fp_vibrato3 = (fp_vibrato3 < 0) ? -fp_vibrato3 : fp_vibrato3;
			int fp_vibrato4 = K32 - (((-vibratoTable[(vph4 >> 15) & WAVE_MASK] + K32) * fp_modVibratoAmp4) >> 15);
			fp_vibrato4 = (fp_vibrato4 < 0) ? -fp_vibrato4 : fp_vibrato4;
			fp_smoothFilterAmp1 = ((fp_smoothFilterAmp1 >> 10) * fp_vibrato1) >> 5;
			fp_smoothFilterAmp2 = ((fp_smoothFilterAmp2 >> 10) * fp_vibrato2) >> 5;
			fp_smoothFilterAmp3 = ((fp_smoothFilterAmp3 >> 10) * fp_vibrato3) >> 5;
			fp_smoothFilterAmp4 = ((fp_smoothFilterAmp4 >> 10) * fp_vibrato4) >> 5;
		}
		if (vibrato2Destination == VIBRATO_DESTINATION_FILTER) {
			int fp_vibrato1 = K32 - (((-vibrato2Table[(v2ph1 >> 15) & WAVE_MASK] + K32) * fp_vibrato2Amp1) >> 15);
			fp_vibrato1 = (fp_vibrato1 < 0) ? -fp_vibrato1 : fp_vibrato1;
			int fp_vibrato2 = K32 - (((-vibrato2Table[(v2ph2 >> 15) & WAVE_MASK] + K32) * fp_vibrato2Amp2) >> 15);
			fp_vibrato2 = (fp_vibrato2 < 0) ? -fp_vibrato2 : fp_vibrato2;
			int fp_vibrato3 = K32 - (((-vibrato2Table[(v2ph3 >> 15) & WAVE_MASK] + K32) * fp_vibrato2Amp3) >> 15);
			fp_vibrato3 = (fp_vibrato3 < 0) ? -fp_vibrato3 : fp_vibrato3;
			int fp_vibrato4 = K32 - (((-vibrato2Table[(v2ph4 >> 15) & WAVE_MASK] + K32) * fp_vibrato2Amp4) >> 15);
			fp_vibrato4 = (fp_vibrato4 < 0) ? -fp_vibrato4 : fp_vibrato4;
			fp_smoothFilterAmp1 = ((fp_smoothFilterAmp1 >> 10) * fp_vibrato1) >> 5;
			fp_smoothFilterAmp2 = ((fp_smoothFilterAmp2 >> 10) * fp_vibrato2) >> 5;
			fp_smoothFilterAmp3 = ((fp_smoothFilterAmp3 >> 10) * fp_vibrato3) >> 5;
			fp_smoothFilterAmp4 = ((fp_smoothFilterAmp4 >> 10) * fp_vibrato4) >> 5;
		}

		float sample1, sample2, sample3, sample4;
		if (mode == MODE_MONOPHONIC) {
			sample1 = smoothamp1 * waveTable[fp_smoothFilterAmp1 >> 15][(ph1 >> 15) & WAVE_MASK] / 16768.0f;
			sample2 = 0;
			sample3 = 0;
			sample4 = 0;
		} else if (mode == MODE_POLYPHONIC) {
			sample1 = smoothamp1 * waveTable[fp_smoothFilterAmp1 >> 15][(ph1 >> 15) & WAVE_MASK] / 16768.0f;
			sample2 = smoothamp2 * waveTable[fp_smoothFilterAmp2 >> 15][(ph2 >> 15) & WAVE_MASK] / 16768.0f;
			sample3 = smoothamp3 * waveTable[fp_smoothFilterAmp3 >> 15][(ph3 >> 15) & WAVE_MASK] / 16768.0f;
			sample4 = smoothamp4 * waveTable[fp_smoothFilterAmp4 >> 15][(ph4 >> 15) & WAVE_MASK] / 16768.0f;
		} else { // chorus
			sample1 = smoothamp1 * waveTable[fp_smoothFilterAmp1 >> 15][(ph1 >> 15) & WAVE_MASK] / 32768.0f;
			sample2 = smoothamp2 * waveTable[fp_smoothFilterAmp2 >> 15][(ph2 >> 15) & WAVE_MASK] / 32768.0f;
			sample3 = smoothamp3 * waveTable[fp_smoothFilterAmp3 >> 15][(ph3 >> 15) & WAVE_MASK] / 32768.0f;
			sample4 = smoothamp4 * waveTable[fp_smoothFilterAmp4 >> 15][(ph4 >> 15) & WAVE_MASK] / 32768.0f;
		}

		// clip
		if (ampOverdrive > 0 && ampOverdrivePerVoice) {
			for (int od = 0; od < ampOverdrive; od++) {
				sample1 *= 2.0f;
				sample2 *= 2.0f;
				sample3 *= 2.0f;
				sample4 *= 2.0f;
			}
			sample1 = range(sample2, -1.0f, 1.0f);
			sample2 = range(sample2, -1.0f, 1.0f);
			sample3 = range(sample3, -1.0f, 1.0f);
			sample4 = range(sample4, -1.0f, 1.0f);
		}
		float sample = sample1 + sample2 + sample3 + sample4;
		if (ampOverdrive > 0 && !ampOverdrivePerVoice) {
			for (int od = 0; od< ampOverdrive; od++) {
				sample *= 2.0f;
			}
			sample = range(sample, -1.0f, 1.0f);
		}

		// adjust the volume based on the echo?
		//sample = sample * (K16 - (fp_echoAmount >> 3) - (fp_echoDelay >> 3)) >> 14;

		float sampleLeft = sample;
		float sampleRight = sample;

		// Echo/flange effect
		flangeIndex += fp_flangeRate;
		int fp_flangingEchoDelay = fp_echoDelay + ((fp_flangeAmount * flangeWave[(flangeIndex >> 11) & 0xFF]) >> 9);
		echoIndex++;
		echoIndex %= sampleRate;
		echoTable[echoIndex] = sample;
		int delayIndexLeft = echoIndex - (fp_flangingEchoDelay >> 1);
		delayIndexLeft = (delayIndexLeft + sampleRate) % sampleRate;
		sampleLeft += echoAmount * echoTable[delayIndexLeft];
		int delayIndexRight = echoIndex - (fp_flangingEchoDelay);
		delayIndexRight = (delayIndexRight + sampleRate) % sampleRate;
		sampleRight += echoAmount * echoTable[delayIndexRight];
		echoTable[echoIndex] += echoFeedback * echoTable[delayIndexRight];

		// Harsh Noise Effects
		
		// Distortion effect (hard clipping/waveshaping)
		if (distortionAmount > 0.0f) {
			float distortThreshold = 1.0f - (distortionAmount * 0.9f); // Lower threshold = more distortion
			if (sampleLeft > distortThreshold) {
				sampleLeft = distortThreshold + (sampleLeft - distortThreshold) * 0.1f;
			} else if (sampleLeft < -distortThreshold) {
				sampleLeft = -distortThreshold + (sampleLeft + distortThreshold) * 0.1f;
			}
			if (sampleRight > distortThreshold) {
				sampleRight = distortThreshold + (sampleRight - distortThreshold) * 0.1f;
			} else if (sampleRight < -distortThreshold) {
				sampleRight = -distortThreshold + (sampleRight + distortThreshold) * 0.1f;
			}
		}
		
		// Bit crush effect (reduce bit depth for lo-fi sound)
		if (bitCrushAmount > 0.0f) {
			float steps = 2.0f + (bitCrushAmount * 30.0f); // 2 to 32 steps
			sampleLeft = Math.round(sampleLeft * steps) / steps;
			sampleRight = Math.round(sampleRight * steps) / steps;
		}
		
		// White noise mixing
		if (whiteNoiseAmount > 0.0f) {
			float noise = (float)(Math.random() * 2.0 - 1.0) * whiteNoiseAmount * K32 * 0.3f;
			sampleLeft += noise;
			sampleRight += noise;
		}

		// Clip in range
		sampleLeft = sampleLeft <= -K32 ? -K32 + 1 : (sampleLeft > K32 ? K32 : sampleLeft);
		sampleRight = sampleRight <= -K32 ? -K32 + 1 : (sampleRight > K32 ? K32 : sampleRight);

		output[0] =  sampleLeft;
		output[1] = sampleRight;
		
		// Update visualizer buffer periodically
		updateVisualizerBuffer(output);

		if (t >= envelopeSampleRate) {
			t = 0;

			// sequencer
			if (sequencerRunning) {
				float lastSequenceIndex = sequenceIndex;
				sequenceIndex += sequenceRate / ENVELOPE_RATE / 60.0f;
				if (sequenceIndex >= SEQUENCE_LENGTH) {
					sequenceIndex = sequenceIndex - SEQUENCE_LENGTH;
				}
				if ((int) lastSequenceIndex != (int) sequenceIndex) {
					sequenceNote = sequence[(int) sequenceIndex];
					if (sequenceNote != 0) {
						if (mode == MODE_MONOPHONIC) {
							internalKeyPress(1, sequencerBaseNote, 1.0f, false, true, 0);
						} else if (mode == MODE_CHORUS) {
							internalKeyPress(1, sequencerBaseNote, 1.0f, false, true, -0.07f * chorusWidth);
							internalKeyPress(2, sequencerBaseNote, 1.0f, false, true, -0.03f * chorusWidth);
							internalKeyPress(3, sequencerBaseNote, 1.0f, false, true, +0.05f * chorusWidth);
							internalKeyPress(4, sequencerBaseNote, 1.0f, false, true, +0.11f * chorusWidth);
						} else {
							internalKeyPress(internalGetVoice(sequencerBaseNote + sequenceNote - 5), sequencerBaseNote, 1.0f, false, true, 0);
						}
					} else {
						internalKeyRelease(1);
						internalKeyRelease(2);
						internalKeyRelease(3);
						internalKeyRelease(4);
					}
					if (!sequenceLoop && ((int) sequenceIndex) == SEQUENCE_LENGTH - 1) {
						sequencerRunning = false;
						internalKeyRelease(1);
						internalKeyRelease(2);
						internalKeyRelease(3);
						internalKeyRelease(4);
					}
				}
			}

			float fp_ampAttack = 0.25f / (ampAttack + 0.1f);
			float fp_ampDecay = 0.25f / (ampDecay + 0.1f);
			float fp_ampRelease = 0.25f / (ampRelease + 0.1f);
			float fp_filterAttack = 0.25f / (filterEnvAttack + 0.1f);
			float fp_filterDecay = 0.25f / (filterEnvDecay + 0.1f);
			float fp_filterRelease = 0.25f / (filterEnvRelease + 0.1f);
			float fp_vibratoAttack = 0.25f / (vibratoAttack + 0.1f);
			float fp_vibratoDecay = 0.25f / (vibratoDecay + 0.1f);
			float fp_vibrato2Attack = 0.25f / (vibrato2Attack + 0.1f);
			float fp_vibrato2Decay = 0.25f / (vibrato2Decay + 0.1f);

			sBend = (sBend * 7 + bend) / 8;
			sVolumeExpression = (sVolumeExpression * 7 + volumeExpression) / 8;
			sFilterExpression = (sFilterExpression * 7 + filterExpression) / 8;

			if (sequenceRate > 0) {
				if (sequenceNote > 0) {
					noteFrequency1 = noteToFrequency(note1 + sequenceNote - 5, key, detune1, sBend);
					noteFrequency2 = noteToFrequency(note2 + sequenceNote - 5, key, detune2, sBend);
					noteFrequency3 = noteToFrequency(note3 + sequenceNote - 5, key, detune3, sBend);
					noteFrequency4 = noteToFrequency(note4 + sequenceNote - 5, key, detune4, sBend);
				}
			} else {
				noteFrequency1 = noteToFrequency(note1, key, detune1, sBend);
				noteFrequency2 = noteToFrequency(note2, key, detune2, sBend);
				noteFrequency3 = noteToFrequency(note3, key, detune3, sBend);
				noteFrequency4 = noteToFrequency(note4, key, detune4, sBend);
			}

			// key 1
			if (gliding1) {
				frequency1 = frequency1 * portamentoAmount + noteFrequency1 * (1.0f - portamentoAmount);
			} else {
				frequency1 = noteFrequency1;
			}
			fp_freq1 = (int) (waveSizeDivSampleRate * frequency1 * K32);
			// amp env 1
			if (keyDown1) {
				if (ampAttacking1) {
					amplitude1 = Math.min(1, amplitude1 + fp_ampAttack);
					if (amplitude1 >= 1) {
						ampAttacking1 = false;
					}
				} else {
					amplitude1 = Math.max(ampSustain, amplitude1 - fp_ampDecay);
				}
			} else {
				amplitude1 = Math.max(0, amplitude1 - fp_ampRelease);
			}
			amp1 = ampVolume * amplitude1 * amplitude1 * amplitude1 / (1.0f + echoAmount) * 96 / (96 + 12 * octave + +key + note1) * velocity1 * sVolumeExpression;
			//filter env1
			if (keyDown1) {
				if (filterAttacking1) {
					filterEnvAmplitude1 = Math.min(1, filterEnvAmplitude1 + fp_filterAttack);
					if (filterEnvAmplitude1 >= 1) {
						filterAttacking1 = false;
					}
				} else {
					filterEnvAmplitude1 = Math.max(filterEnvSustain, filterEnvAmplitude1 - fp_filterDecay);
				}
			} else {
				filterEnvAmplitude1 = Math.max(0, filterEnvAmplitude1 - fp_filterRelease);
			}
//							if (filterFixed) {
//								float freqAdjust = (frequency1 - 32 * C0) / (32 * C0) / 8;
//								filterEnvAmplitude1 -= freqAdjust;
//							}
			filterAmplitude1 = range(filterLow + filterEnvAmplitude1 * filterHigh * filterVelocity1 + sFilterExpression, 0, 0.99f);
			fp_filterAmp1 = (int) (TABLE_SIZE * filterAmplitude1 * K32);
			// vib env 1
			if (keyDown1) {
				if (vibratoAttacking1) {
					vibratoAmplitude1 = Math.min(1, vibratoAmplitude1 + fp_vibratoAttack);
					if (vibratoAmplitude1 >= 1) {
						vibratoAttacking1 = false;
					}
				} else {
					vibratoAmplitude1 = Math.max(0, vibratoAmplitude1 - fp_vibratoDecay);
				}
			} else {
				vibratoAmplitude1 = Math.max(0, vibratoAmplitude1 - fp_vibratoDecay);
			}
			fp_vibratoAmp1 = (int) ((vibratoAmplitude1 * vibratoAmount + vibratoExpression) * K32);
			// vib2 env 1
			if (keyDown1) {
				if (vibrato2Attacking1) {
					vibrato2Amplitude1 = Math.min(1, vibrato2Amplitude1 + fp_vibrato2Attack);
					if (vibrato2Amplitude1 >= 1) {
						vibrato2Attacking1 = false;
					}
				} else {
					vibrato2Amplitude1 = Math.max(0, vibrato2Amplitude1 - fp_vibrato2Decay);
				}
			} else {
				vibrato2Amplitude1 = Math.max(0, vibrato2Amplitude1 - fp_vibrato2Decay);
			}
			fp_vibrato2Amp1 = (int) ((vibrato2Amplitude1 * vibrato2Amount + vibrato2Expression) * K32);
			if (releaseKey1) {
				keyDown1 = false;
				releaseKey1 = false;
			}

			// key 2
			if (gliding2) {
				frequency2 = frequency2 * portamentoAmount + noteFrequency2 * (1.0f - portamentoAmount);
			} else {
				frequency2 = noteFrequency2;
			}
			fp_freq2 = (int) (waveSizeDivSampleRate * frequency2 * K32);
			if (keyDown2) {
				if (ampAttacking2) {
					amplitude2 = Math.min(1, amplitude2 + fp_ampAttack);
					if (amplitude2 >= 1) {
						ampAttacking2 = false;
					}
				} else {
					amplitude2 = Math.max(ampSustain, amplitude2 - fp_ampDecay);
				}
			} else {
				amplitude2 = Math.max(0, amplitude2 - fp_ampRelease);
			}
			amp2 = ampVolume * amplitude2 * amplitude2 * amplitude2 / (1.0f + echoAmount) * 96 / (96 + 12 * octave + key + note2) * velocity2 * sVolumeExpression;
			if (keyDown2) {
				if (filterAttacking2) {
					filterEnvAmplitude2 = Math.min(1, filterEnvAmplitude2 + fp_filterAttack);
					if (filterEnvAmplitude2 >= 1) {
						filterAttacking2 = false;
					}
				} else {
					filterEnvAmplitude2 = Math.max(filterEnvSustain, filterEnvAmplitude2 - fp_filterDecay);
				}
			} else {
				filterEnvAmplitude2 = Math.max(0, filterEnvAmplitude2 - fp_filterRelease);
			}
			filterAmplitude2 = range(filterLow + filterEnvAmplitude2 * filterHigh * filterVelocity2 + sFilterExpression, 0, 0.99f);
			fp_filterAmp2 = (int) (TABLE_SIZE * filterAmplitude2 * K32);
			// vib env 2
			if (keyDown2) {
				if (vibratoAttacking2) {
					vibratoAmplitude2 = Math.min(1, vibratoAmplitude2 + fp_vibratoAttack);
					if (vibratoAmplitude2 >= 1) {
						vibratoAttacking2 = false;
					}
				} else {
					vibratoAmplitude2 = Math.max(0, vibratoAmplitude2 - fp_vibratoDecay);
				}
			} else {
				vibratoAmplitude2 = Math.max(0, vibratoAmplitude2 - fp_vibratoDecay);
			}
			fp_vibratoAmp2 = (int) ((vibratoAmplitude2 * vibratoAmount + vibratoExpression) * K32);
			// vib2 env 2
			if (keyDown2) {
				if (vibrato2Attacking2) {
					vibrato2Amplitude2 = Math.min(1, vibrato2Amplitude2 + fp_vibrato2Attack);
					if (vibrato2Amplitude2 >= 1) {
						vibrato2Attacking2 = false;
					}
				} else {
					vibrato2Amplitude2 = Math.max(0, vibrato2Amplitude2 - fp_vibrato2Decay);
				}
			} else {
				vibrato2Amplitude2 = Math.max(0, vibrato2Amplitude2 - fp_vibrato2Decay);
			}
			fp_vibrato2Amp2 = (int) ((vibrato2Amplitude2 * vibrato2Amount + vibrato2Expression) * K32);
			if (releaseKey2) {
				keyDown2 = false;
				releaseKey2 = false;
			}

			// key 3
			if (gliding3) {
				frequency3 = frequency3 * portamentoAmount + noteFrequency3 * (1.0f - portamentoAmount);
			} else {
				frequency3 = noteFrequency3;
			}
			fp_freq3 = (int) (waveSizeDivSampleRate * frequency3 * K32);
			if (keyDown3) {
				if (ampAttacking3) {
					amplitude3 = Math.min(1, amplitude3 + fp_ampAttack);
					if (amplitude3 >= 1) {
						ampAttacking3 = false;
					}
				} else {
					amplitude3 = Math.max(ampSustain, amplitude3 - fp_ampDecay);
				}
			} else {
				amplitude3 = Math.max(0, amplitude3 - fp_ampRelease);
			}
			amp3 = ampVolume * amplitude3 * amplitude3 * amplitude3 / (1.0f + echoAmount) * 96 / (96 + 12 * octave + key + note3) * velocity3 * sVolumeExpression;
			if (keyDown3) {
				if (filterAttacking3) {
					filterEnvAmplitude3 = Math.min(1, filterEnvAmplitude3 + fp_filterAttack);
					if (filterEnvAmplitude3 >= 1) {
						filterAttacking3 = false;
					}
				} else {
					filterEnvAmplitude3 = Math.max(filterEnvSustain, filterEnvAmplitude3 - fp_filterDecay);
				}
			} else {
				filterEnvAmplitude3 = Math.max(0, filterEnvAmplitude3 - fp_filterRelease);
			}
			filterAmplitude3 = range(filterLow + filterEnvAmplitude3 * filterHigh * filterVelocity3 + sFilterExpression, 0, 0.99f);
			fp_filterAmp3 = (int) (TABLE_SIZE * filterAmplitude3 * K32);
			// vib env 3
			if (keyDown3) {
				if (vibratoAttacking3) {
					vibratoAmplitude3 = Math.min(1, vibratoAmplitude3 + fp_vibratoAttack);
					if (vibratoAmplitude3 >= 1) {
						vibratoAttacking3 = false;
					}
				} else {
					vibratoAmplitude3 = Math.max(0, vibratoAmplitude3 - fp_vibratoDecay);
				}
			} else {
				vibratoAmplitude3 = Math.max(0, vibratoAmplitude3 - fp_vibratoDecay);
			}
			fp_vibratoAmp3 = (int) ((vibratoAmplitude3 * vibratoAmount + vibratoExpression) * K32);
			// vib2 env 3
			if (keyDown3) {
				if (vibrato2Attacking3) {
					vibrato2Amplitude3 = Math.min(1, vibrato2Amplitude3 + fp_vibrato2Attack);
					if (vibrato2Amplitude3 >= 1) {
						vibrato2Attacking3 = false;
					}
				} else {
					vibrato2Amplitude3 = Math.max(0, vibrato2Amplitude3 - fp_vibrato2Decay);
				}
			} else {
				vibrato2Amplitude3 = Math.max(0, vibrato2Amplitude3 - fp_vibrato2Decay);
			}
			fp_vibrato2Amp3 = (int) ((vibrato2Amplitude3 * vibrato2Amount + vibrato2Expression) * K32);
			if (releaseKey3) {
				keyDown3 = false;
				releaseKey3 = false;
			}

			// key 4
			if (gliding4) {
				frequency4 = frequency4 * portamentoAmount + noteFrequency4 * (1.0f - portamentoAmount);
			} else {
				frequency4 = noteFrequency4;
			}
			fp_freq4 = (int) (waveSizeDivSampleRate * frequency4 * K32);
			if (keyDown4) {
				if (ampAttacking4) {
					amplitude4 = Math.min(1, amplitude4 + fp_ampAttack);
					if (amplitude4 >= 1) {
						ampAttacking4 = false;
					}
				} else {
					amplitude4 = Math.max(ampSustain, amplitude4 - fp_ampDecay);
				}
			} else {
				amplitude4 = Math.max(0, amplitude4 - fp_ampRelease);
			}
			amp4 = ampVolume * amplitude4 * amplitude4 * amplitude4 / (1.0f + echoAmount) * 96 / (96 + 12 * octave + key + note4) * velocity4 * sVolumeExpression;
			if (keyDown4) {
				if (filterAttacking4) {
					filterEnvAmplitude4 = Math.min(1, filterEnvAmplitude4 + fp_filterAttack);
					if (filterEnvAmplitude4 >= 1) {
						filterAttacking4 = false;
					}
				} else {
					filterEnvAmplitude4 = Math.max(filterEnvSustain, filterEnvAmplitude4 - fp_filterDecay);
				}
			} else {
				filterEnvAmplitude4 = Math.max(0, filterEnvAmplitude4 - fp_filterRelease);
			}
			filterAmplitude4 = range(filterLow + filterEnvAmplitude4 * filterHigh * filterVelocity4 + sFilterExpression, 0, 0.99f);
			fp_filterAmp4 = (int) (TABLE_SIZE * filterAmplitude4 * K32);
			// vib env 4
			if (keyDown4) {
				if (vibratoAttacking4) {
					vibratoAmplitude4 = Math.min(1, vibratoAmplitude4 + fp_vibratoAttack);
					if (vibratoAmplitude4 >= 1) {
						vibratoAttacking4 = false;
					}
				} else {
					vibratoAmplitude4 = Math.max(0, vibratoAmplitude4 - fp_vibratoDecay);
				}
			} else {
				vibratoAmplitude4 = Math.max(0, vibratoAmplitude4 - fp_vibratoDecay);
			}
			fp_vibratoAmp4 = (int) ((vibratoAmplitude4 * vibratoAmount + vibratoExpression) * K32);
			// vib2 env 4
			if (keyDown4) {
				if (vibrato2Attacking4) {
					vibrato2Amplitude4 = Math.min(1, vibrato2Amplitude4 + fp_vibrato2Attack);
					if (vibrato2Amplitude4 >= 1) {
						vibrato2Attacking4 = false;
					}
				} else {
					vibrato2Amplitude4 = Math.max(0, vibrato2Amplitude4 - fp_vibrato2Decay);
				}
			} else {
				vibrato2Amplitude4 = Math.max(0, vibrato2Amplitude4 - fp_vibrato2Decay);
			}
			fp_vibrato2Amp4 = (int) ((vibrato2Amplitude4 * vibrato2Amount + vibrato2Expression) * K32);
			if (releaseKey4) {
				keyDown4 = false;
				releaseKey4 = false;
			}

			if (vibratoType == VIBRATO_TYPE_NOISE) {
				fp_vibratoRate = (int) (waveSizeDivSampleRate * vibratoRate * K32 / 100);
			} else {
				fp_vibratoRate = (int) (waveSizeDivSampleRate * vibratoRate * K32);
			}
			if (vibrato2Type == VIBRATO_TYPE_NOISE) {
				fp_vibrato2Rate = (int) (waveSizeDivSampleRate * vibrato2Rate * K32 / 100);
			} else {
				fp_vibrato2Rate = (int) (waveSizeDivSampleRate * vibrato2Rate * K32);
			}

			fp_echoDelay = (int) (echoDelay * sampleRate);
			fp_flangeRate = (int) (flangeRate * 256);
			fp_flangeAmount = (int) (flangeAmount * 256);

		}
	}

	float range(float v, float x, float y) {
		return v < x ? x : (v > y ? y : v);
	}

	float noteToFrequency(int note, int key, float detune, float bend) {
		if (tuning == TUNING_EQUAL_TEMPERAMENT) {
			return (float) (Math.pow(2, (note + key) / 12.0f + octave + tuningCents / 1200.0 + (detune + bend))) * C0;
		} else if (tuning == TUNING_JUST_INTONATION) { // this is 5 limit tuning
			int octaveAdjusted = octave;
			while (note < 0) {
				note += 12;
				octaveAdjusted -= 1;
			}
			while (note >= 12) {
				note -= 12;
				octaveAdjusted += 1;
			}
			float freq;
			if (note == 0) {
				freq = C0;
			} else if (note == 1) {
				freq = C0 * 16.0f / 15.0f;
			} else if (note == 2) {
				freq = C0 * 9.0f / 8.0f; // 5 limit
//				freq = C0 * 8.0f / 7.0f;  // 7 limit
			} else if (note == 3) {
				freq = C0 * 6.0f / 5.0f;
			} else if (note == 4) {
				freq = C0 * 5.0f / 4.0f;
			} else if (note == 5) {
				freq = C0 * 4.0f / 3.0f;
			} else if (note == 6) {
//				freq = C0 * 7.0f / 5.0f; // augmented fourth (vs 10/7 for diminished fifth)
				freq = C0 * 1.4142f; // sqrt, see nicksworldofsynthesizers.com/flashorgan.php
			} else if (note == 7) {
				freq = C0 * 3.0f / 2.0f;
			} else if (note == 8) {
				freq = C0 * 8.0f / 5.0f;
			} else if (note == 9) {
				freq = C0 * 5.0f / 3.0f;
			} else if (note == 10) {
				freq = C0 * 9.0f / 5.0f; // 5 limit
//				freq = C0 * 7.0f / 4.0f;  // 7 limit
			} else if (note == 11) {
				freq = C0 * 15.0f / 8.0f;
			} else {
				freq = C0 * 2.0f;
			}
			return (float) (Math.pow(2, key / 12.0f + octaveAdjusted + tuningCents / 1200.0 + (detune + bend))) * freq;
		} else if (tuning == TUNING_BAD) {
			int octaveAdjusted = octave;
			while (note < 0) {
				note += 12;
				octaveAdjusted -= 1;
			}
			while (note >= 12) {
				note -= 12;
				octaveAdjusted += 1;
			}
			float freq;
			if (note == 0) {
				freq = C0;
			} else if (note == 1) {
				freq = C0 * 16.0f / 15.0f;
			} else if (note == 2) {
				freq = C0 * 90.0f / 81.0f;
			} else if (note == 3) {
				freq = C0 * 60.0f / 51.0f;
			} else if (note == 4) {
				freq = C0 * 50.0f / 41.0f;
			} else if (note == 5) {
				freq = C0 * 40.0f / 31.0f;
			} else if (note == 6) {
				freq = C0 * 70.0f / 51.0f;
			} else if (note == 7) {
				freq = C0 * 30.0f / 21.0f;
			} else if (note == 8) {
				freq = C0 * 80.0f / 51.0f;
			} else if (note == 9) {
				freq = C0 * 50.0f / 31.0f;
			} else if (note == 10) {
				freq = C0 * 70.0f / 41.0f;
			} else if (note == 11) {
				freq = C0 * 15.0f / 8.0f;
			} else {
				freq = C0 * 2.0f;
			}
			return (float) (Math.pow(2, key / 12.0f + octaveAdjusted + tuningCents / 1200.0 + (detune + bend))) * freq;
		}
		return 0.0f;
	}

	void internalNoteChange(int voice, int note, float velocity, float detune) {
		if (voice == 1) {
			note1 = note;
			if (sequenceNote > 0) {
				noteFrequency1 = noteToFrequency(note1 + sequenceNote - 5, key, detune, bend);
			} else {
				noteFrequency1 = noteToFrequency(note1, key, detune, bend);
			}
			velocity1 = 1;
			filterVelocity1 = 1;
			if (velocityType == VELOCITY_VOLUME) {
				velocity1 = velocity;
			} else if (velocityType == VELOCITY_FILTER) {
				filterVelocity1 = velocity;
			} else if (velocityType == VELOCITY_BOTH) {
				velocity1 = velocity;
				filterVelocity1 = velocity;
			}
			detune1 = detune;
		} else if (voice == 2) {
			note2 = note;
			if (sequenceNote > 0) {
				noteFrequency2 = noteToFrequency(note2 + sequenceNote - 5, key, detune, bend);
			} else {
				noteFrequency2 = noteToFrequency(note2, key, detune, bend);
			}
			velocity2 = 1;
			filterVelocity2 = 1;
			if (velocityType == VELOCITY_VOLUME) {
				velocity2 = velocity;
			} else if (velocityType == VELOCITY_FILTER) {
				filterVelocity2 = velocity;
			} else if (velocityType == VELOCITY_BOTH) {
				velocity2 = velocity;
				filterVelocity2 = velocity;
			}
			detune2 = detune;
		} else if (voice == 3) {
			note3 = note;
			if (sequenceNote > 0) {
				noteFrequency3 = noteToFrequency(note3 + sequenceNote - 5, key, detune, bend);
			} else {
				noteFrequency3 = noteToFrequency(note3, key, detune, bend);
			}
			velocity3 = 1;
			filterVelocity3 = 1;
			if (velocityType == VELOCITY_VOLUME) {
				velocity3 = velocity;
			} else if (velocityType == VELOCITY_FILTER) {
				filterVelocity3 = velocity;
			} else if (velocityType == VELOCITY_BOTH) {
				velocity3 = velocity;
				filterVelocity3 = velocity;
			}
			detune3 = detune;
		} else {
			note4 = note;
			if (sequenceNote > 0) {
				noteFrequency4 = noteToFrequency(note4 + sequenceNote - 5, key, detune, bend);
			} else {
				noteFrequency4 = noteToFrequency(note4, key, detune, bend);
			}
			velocity4 = 1;
			filterVelocity4 = 1;
			if (velocityType == VELOCITY_VOLUME) {
				velocity4 = velocity;
			} else if (velocityType == VELOCITY_FILTER) {
				filterVelocity4 = velocity;
			} else if (velocityType == VELOCITY_BOTH) {
				velocity4 = velocity;
				filterVelocity4 = velocity;
			}
			detune4 = detune;
		}
	}

	void internalResetVibrato(int voice) {
		if (voice == 1) {
			vph1 = 0;
			v2ph1 = 0;
		} else if (voice == 2) {
			vph2 = 0;
			v2ph2 = 0;
		} else if (voice == 3) {
			vph3 = 0;
			v2ph3 = 0;
		} else {
			vph4 = 0;
			v2ph4 = 0;
		}
	}

	void internalKeyPress(int voice, int note, float velocity, boolean glide, boolean startsSilent, float detune) {
		firstExpression = true;
		if (voice == 1) {
			internalNoteChange(1, note, velocity, detune);
			keyDown1 = true;
			if (!glide || portamentoAmount == 0) {
				ampAttacking1 = true;
				filterAttacking1 = true;
				vibratoAttacking1 = true;
				vibrato2Attacking1 = true;
				internalResetVibrato(1);
			}
			if (startsSilent) {
				amplitude1 = 0;
				filterEnvAmplitude1 = 0;
				vibratoAmplitude1 = 0;
				vibrato2Amplitude1 = 0;
			}
			gliding1 = glide;
		} else if (voice == 2) {
			internalNoteChange(2, note, velocity, detune);
			keyDown2 = true;
			if (!glide || portamentoAmount == 0) {
				ampAttacking2 = true;
				filterAttacking2 = true;
				vibratoAttacking2 = true;
				vibrato2Attacking2 = true;
				internalResetVibrato(2);
			}
			if (startsSilent) {
				amplitude2 = 0;
				filterEnvAmplitude2 = 0;
				vibratoAmplitude2 = 0;
				vibrato2Amplitude2 = 0;
			}
			gliding2 = glide;
		} else if (voice == 3) {
			internalNoteChange(3, note, velocity, detune);
			keyDown3 = true;
			if (!glide || portamentoAmount == 0) {
				ampAttacking3 = true;
				filterAttacking3 = true;
				vibratoAttacking3 = true;
				vibrato2Attacking3 = true;
				internalResetVibrato(3);
			}
			if (startsSilent) {
				amplitude3 = 0;
				filterEnvAmplitude3 = 0;
				vibratoAmplitude3 = 0;
				vibrato2Amplitude3 = 0;
			}
			gliding3 = glide;
		} else {
			internalNoteChange(4, note, velocity, detune);
			keyDown4 = true;
			if (!glide || portamentoAmount == 0) {
				ampAttacking4 = true;
				filterAttacking4 = true;
				vibratoAttacking4 = true;
				vibrato2Attacking4 = true;
				internalResetVibrato(4);
			}
			if (startsSilent) {
				amplitude4 = 0;
				filterEnvAmplitude4 = 0;
				vibratoAmplitude4 = 0;
				vibrato2Amplitude4 = 0;
			}
			gliding4 = glide;
		}
	}

	void internalKeyRelease(int voice) {
		if (voice == 1) {
			releaseKey1 = true;
		} else if (voice == 2) {
			releaseKey2 = true;
		} else if (voice == 3) {
			releaseKey3 = true;
		} else {
			releaseKey4 = true;
		}
	}

	void internalPitchBend(float jbend) {
		bend = jbend;
		if (firstExpression) {
			sBend = bend;
		}
//		internalNoteChange(1, note1, velocity1, detune1);
//		internalNoteChange(2, note2, velocity2, detune2);
//		internalNoteChange(3, note3, velocity3, detune3);
//		internalNoteChange(4, note4, velocity4, detune4);
	}

	int internalGetVoice(int note) {
		int voice = 1;
		if (note == note1) {
			voice = 1;
		} else if (note == note2) {
			voice = 2;
		} else if (note == note3) {
			voice = 3;
		} else if (note == note4) {
			voice = 4;
		} else {
			// use the quietest voice
			float amp = 0;
			int i;
			for (i = 1; i <= 4; i++) {
				if (i == 1) {
					amp = amplitude1 + (keyDown1 ? 1 : 0);
					voice = 1;
				} else if (i == 2) {
					float synth2amp = amplitude2 + (keyDown2 ? 1 : 0);
					if (synth2amp < amp) {
						amp = synth2amp;
						voice = 2;
					}
				} else if (i == 3) {
					float synth3amp = amplitude3 + (keyDown3 ? 1 : 0);
					if (synth3amp < amp) {
						amp = synth3amp;
						voice = 3;
					}
				} else { // 4
					float synth4amp = amplitude4 + (keyDown4 ? 1 : 0);
					if (synth4amp < amp) {
						voice = 4;
					}

				}
			}
		}
		return voice;
	}
	
	public short[] getLastAudioBuffer() {
		return lastAudioBuffer;
	}
	
	public void updateVisualizerBuffer(float[] floatBuffer) {
		if (floatBuffer == null || floatBuffer.length == 0) return;
		
		if (lastAudioBuffer == null) {
			lastAudioBuffer = new short[VISUALIZER_BUFFER_SIZE];
			visualizerBufferIndex = 0;
		}
		
		// Accumulate samples into buffer (using left channel only)
		float sample = floatBuffer[0];
		// Clamp to -1.0 to 1.0
		sample = Math.max(-1.0f, Math.min(1.0f, sample));
		// Convert to short and store
		lastAudioBuffer[visualizerBufferIndex] = (short) (sample * 32767f);
		
		// Move to next position, wrap around when full
		visualizerBufferIndex = (visualizerBufferIndex + 1) % VISUALIZER_BUFFER_SIZE;
	}

}

