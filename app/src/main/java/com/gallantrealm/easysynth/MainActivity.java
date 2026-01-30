package com.gallantrealm.easysynth;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.gallantrealm.android.ContentUriUtil;
import com.gallantrealm.easysynth.theme.AuraTheme;
import com.gallantrealm.easysynth.theme.CustomTheme;
import com.gallantrealm.easysynth.theme.IceTheme;
import com.gallantrealm.easysynth.theme.MetalTheme;
import com.gallantrealm.easysynth.theme.SpaceTheme;
import com.gallantrealm.android.InputDialog;
import com.gallantrealm.android.MessageDialog;
import com.gallantrealm.mysynth.MySynth;
import com.gallantrealm.mysynth.MySynthMidi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowInsets;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

//import com.newrelic.agent.android.NewRelic;

public class MainActivity extends Activity implements OnTouchListener, OnSeekBarChangeListener, OnCheckedChangeListener, View.OnClickListener, MySynthMidi.Callbacks {

	public ClientModel clientModel = ClientModel.getClientModel();

	View mainLayout;
	TextView cpuOverlay;
	Thread cpuMonitorThread;
	
	Sound sound; // last sound loaded
	long soundLoadTime; // time sound was loaded

	View buttonPane;
	Button buyButton;
	Button soundButton;
	Button harmonicsButton;
	Button filterButton;
	Button ampButton;
	Button vibratoButton;
	Button vibrato2Button;
	Button echoButton;
	Button sequencerButton;
	Button recordButton;
	Button settingsButton;
	View soundPane;
	View harmonicsPane;
	View filterPane;
	View ampPane;
	View vibratoPane;
	View vibrato2Pane;
	View echoPane;
	View effectsPane;
	Button newEffectsButton;
	View newEffectsPane;
	View sequencerPane;
	View recordPane;
	View keyboardPane;

	Spinner soundSpinner;
	Button saveButton;
	Button sendButton;
	Button deleteButton;

	RadioButton monoRadio;
	RadioButton polyRadio;
	RadioButton chorusRadio;
	SeekBar chorusWidth;
	SeekBar portamentoAmount;
	Spinner velocitySpinner;
	Spinner expressionSpinner;
	RadioButton octave2Radio;
	RadioButton octave3Radio;
	RadioButton octave4Radio;
	RadioButton octave5Radio;
	RadioButton octave6Radio;
	CheckBox holdButton;
	Spinner keySpinner;

	SeekBar harmonic1;
	SeekBar harmonic2;
	SeekBar harmonic3;
	SeekBar harmonic4;
	SeekBar harmonic5;
	SeekBar harmonic6;
	SeekBar harmonic7;
	SeekBar harmonic8;
	SeekBar harmonic9;
	SeekBar harmonic10;
	SeekBar harmonic11;
	SeekBar harmonic12;
	SeekBar harmonic13;
	SeekBar harmonic14;
	SeekBar harmonic15;
	SeekBar harmonic16;
	SeekBar harmonic17;
	SeekBar harmonic18;
	SeekBar harmonic19;
	SeekBar harmonic20;
	SeekBar harmonic21;
	SeekBar harmonic22;
	SeekBar harmonic23;
	SeekBar harmonic24;
	SeekBar harmonic25;
	SeekBar harmonic26;
	SeekBar harmonic27;
	SeekBar harmonic28;
	SeekBar harmonic29;
	SeekBar harmonic30;
	SeekBar harmonic31;
	SeekBar harmonic32;

	Spinner filterTypeSpinner;
	SeekBar filterResonance;
	SeekBar filterLow;
	SeekBar filterHigh;
	SeekBar filterEnvAttack;
	SeekBar filterEnvDecay;
	SeekBar filterEnvSustain;
	SeekBar filterEnvRelease;

	SeekBar ampVolume;
	SeekBar ampAttack;
	SeekBar ampDecay;
	SeekBar ampSustain;
	SeekBar ampRelease;
	SeekBar ampOverdrive;
	CheckBox ampOverdrivePerVoiceCheckBox;

	Spinner vibratoDestinationSpinner;
	Spinner vibratoTypeSpinner;
	SeekBar vibratoAmount;
	SeekBar vibratoRate;
	CheckBox vibratoSyncCheckBox;
	SeekBar vibratoAttack;
	SeekBar vibratoDecay;

	Spinner vibrato2DestinationSpinner;
	Spinner vibrato2TypeSpinner;
	SeekBar vibrato2Amount;
	SeekBar vibrato2Rate;
	CheckBox vibrato2SyncCheckBox;
	SeekBar vibrato2Attack;
	SeekBar vibrato2Decay;

	SeekBar echoAmount;
	SeekBar echoDelay;
	SeekBar echoFeedback;
	SeekBar flangeAmount;
	SeekBar flangeRate;
	SeekBar distortionAmount;
	SeekBar bitCrush;
	CheckBox whiteNoiseCheckBox;
	SeekBar pinkNoiseAmount;
	SeekBar brownNoiseAmount;
	SeekBar staticNoiseAmount;
	SeekBar reverbAmount;
	SeekBar reverbRoomSize;
	SeekBar reverbDamping;
	SeekBar limiterAmount;
	SeekBar limiterThreshold;
	SeekBar limiterRelease;
	SeekBar compressorAmount;
	SeekBar compressorRatio;
	SeekBar compressorAttack;

	SeekBar sequence1;
	SeekBar sequence2;
	SeekBar sequence3;
	SeekBar sequence4;
	SeekBar sequence5;
	SeekBar sequence6;
	SeekBar sequence7;
	SeekBar sequence8;
	SeekBar sequence9;
	SeekBar sequence10;
	SeekBar sequence11;
	SeekBar sequence12;
	SeekBar sequence13;
	SeekBar sequence14;
	SeekBar sequence15;
	SeekBar sequence16;
	// SeekBar sequenceRate;
	Button sequenceRateUpButton;
	Button sequenceRateDownButton;
	TextView sequenceRateTextView;
	CheckBox sequenceLoopCheckBox;

	TextView recordTime;
	Button recordStartButton;
	Button recordStopButton;
	Button recordPlayButton;
	Button recordSaveButton;

	View keyboard;
	int keyboardLocation[];
	Button key1;
	Button key2;
	Button key3;
	Button key4;
	Button key5;
	Button key6;
	Button key7;
	Button key8;
	Button key9;
	Button key10;
	Button key11;
	Button key12;
	Button key13;
	Button key14;
	Button key15;
	Button key16;
	Button key17;
	Button key18;
	Button key19;
	Button key20;
	Button key21;
	Button key22;
	Button key23;
	Button key24;
	Button key25;
	Button key26;
	Button key27;
	Button key28;
	Button key29;
	Button key30;
	Button key31;
	Button key32;

	int[] keyvoice = new int[25];

	private MySynth synth;
	private MySynthMidi midi;
	WaveSynth instrument;

	private boolean dirty;

	int myMidiChannel;

	PowerManager.WakeLock wakelock;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.synth_screen);

		clientModel.loadPreferences(this);

		clientModel.migrateInternalFilesToExternal();

		synth = MySynth.create(this);
		midi = MySynthMidi.create(this, synth, this);
		instrument = new WaveSynth(this);

		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

		mainLayout = findViewById(R.id.mainLayout);
		com.gallantrealm.android.Translator.setTranslator(new Translator());

		com.gallantrealm.android.Translator.getTranslator().translate(mainLayout);

		buyButton = (Button) findViewById(R.id.buyButton);
		soundButton = (Button) findViewById(R.id.soundButton);
		harmonicsButton = (Button) findViewById(R.id.harmonicsButton);
		filterButton = (Button) findViewById(R.id.filterButton);
		ampButton = (Button) findViewById(R.id.ampButton);
		vibratoButton = (Button) findViewById(R.id.vibratoButton);
		vibrato2Button = (Button) findViewById(R.id.vibrato2Button);
		echoButton = (Button) findViewById(R.id.effectsButton);
		sequencerButton = (Button) findViewById(R.id.sequencerButton);
		recordButton = (Button) findViewById(R.id.recordButton);
		settingsButton = (Button) findViewById(R.id.settingsButton);

		soundPane = findViewById(R.id.soundPane);
		harmonicsPane = findViewById(R.id.harmonicsPane);
		filterPane = findViewById(R.id.filterPane);
		ampPane = findViewById(R.id.ampPane);
		vibratoPane = findViewById(R.id.vibratoPane);
		vibrato2Pane = findViewById(R.id.vibrato2Pane);
		echoPane = findViewById(R.id.effectsPane);
		effectsPane = findViewById(R.id.effectsPane);
		newEffectsButton = (Button) findViewById(R.id.newEffectsButton);
		newEffectsPane = findViewById(R.id.newEffectsPane);
		sequencerPane = findViewById(R.id.sequencerPane);
		recordPane = findViewById(R.id.recordPane);
		keyboardPane = findViewById(R.id.keyboardPane);

		soundSpinner = (Spinner) soundPane.findViewById(R.id.soundSpinner);
		saveButton = (Button) soundPane.findViewById(R.id.saveButton);
		sendButton = (Button) soundPane.findViewById(R.id.sendButton);
		deleteButton = (Button) soundPane.findViewById(R.id.deleteButton);

		monoRadio = (RadioButton) soundPane.findViewById(R.id.monoRadio);
		polyRadio = (RadioButton) soundPane.findViewById(R.id.polyRadio);
		chorusRadio = (RadioButton) soundPane.findViewById(R.id.chorusRadio);
		chorusWidth = (SeekBar) soundPane.findViewById(R.id.chorusWidth);
		portamentoAmount = (SeekBar) soundPane.findViewById(R.id.portamentoAmount);
		velocitySpinner = (Spinner) soundPane.findViewById(R.id.velocitySpinner);
		expressionSpinner = (Spinner) soundPane.findViewById(R.id.expressionSpinner);
		octave2Radio = (RadioButton) soundPane.findViewById(R.id.octave2Radio);
		octave3Radio = (RadioButton) soundPane.findViewById(R.id.octave3Radio);
		octave4Radio = (RadioButton) soundPane.findViewById(R.id.octave4Radio);
		octave5Radio = (RadioButton) soundPane.findViewById(R.id.octave5Radio);
		octave6Radio = (RadioButton) soundPane.findViewById(R.id.octave6Radio);
		holdButton = (CheckBox) soundPane.findViewById(R.id.holdButton);
		keySpinner = (Spinner) soundPane.findViewById(R.id.keySpinner);

		harmonic1 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic1);
		harmonic2 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic2);
		harmonic3 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic3);
		harmonic4 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic4);
		harmonic5 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic5);
		harmonic6 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic6);
		harmonic7 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic7);
		harmonic8 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic8);
		harmonic9 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic9);
		harmonic10 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic10);
		harmonic11 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic11);
		harmonic12 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic12);
		harmonic13 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic13);
		harmonic14 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic14);
		harmonic15 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic15);
		harmonic16 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic16);
		harmonic17 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic17);
		harmonic18 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic18);
		harmonic19 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic19);
		harmonic20 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic20);
		harmonic21 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic21);
		harmonic22 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic22);
		harmonic23 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic23);
		harmonic24 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic24);
		harmonic25 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic25);
		harmonic26 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic26);
		harmonic27 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic27);
		harmonic28 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic28);
		harmonic29 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic29);
		harmonic30 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic30);
		harmonic31 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic31);
		harmonic32 = (SeekBar) harmonicsPane.findViewById(R.id.harmonic32);

		filterTypeSpinner = (Spinner) filterPane.findViewById(R.id.filterTypeSpinner);
		filterResonance = (SeekBar) filterPane.findViewById(R.id.filterResonance);
		filterLow = (SeekBar) filterPane.findViewById(R.id.filterLow);
		filterHigh = (SeekBar) filterPane.findViewById(R.id.filterHigh);
		filterEnvAttack = (SeekBar) filterPane.findViewById(R.id.filterEnvAttack);
		filterEnvDecay = (SeekBar) filterPane.findViewById(R.id.filterEnvDecay);
		filterEnvSustain = (SeekBar) filterPane.findViewById(R.id.filterEnvSustain);
		filterEnvRelease = (SeekBar) filterPane.findViewById(R.id.filterEnvRelease);

		ampVolume = (SeekBar) ampPane.findViewById(R.id.ampVolume);
		ampAttack = (SeekBar) ampPane.findViewById(R.id.ampAttack);
		ampDecay = (SeekBar) ampPane.findViewById(R.id.ampDecay);
		ampSustain = (SeekBar) ampPane.findViewById(R.id.ampSustain);
		ampRelease = (SeekBar) ampPane.findViewById(R.id.ampRelease);
		ampOverdrive = (SeekBar) ampPane.findViewById(R.id.ampOverdrive);
		ampOverdrivePerVoiceCheckBox = (CheckBox) ampPane.findViewById(R.id.ampOverdriverPerVoiceCheckBox);

		vibratoDestinationSpinner = (Spinner) vibratoPane.findViewById(R.id.vibratoDestinationSpinner);
		vibratoTypeSpinner = (Spinner) vibratoPane.findViewById(R.id.vibratoTypeSpinner);
		vibratoAmount = (SeekBar) vibratoPane.findViewById(R.id.vibratoAmount);
		vibratoRate = (SeekBar) vibratoPane.findViewById(R.id.vibratoRate);
		vibratoSyncCheckBox = (CheckBox) vibratoPane.findViewById(R.id.vibratoSyncCheckBox);
		vibratoAttack = (SeekBar) vibratoPane.findViewById(R.id.vibratoAttack);
		vibratoDecay = (SeekBar) vibratoPane.findViewById(R.id.vibratoDecay);

		vibrato2DestinationSpinner = (Spinner) vibrato2Pane.findViewById(R.id.vibratoDestinationSpinner);
		vibrato2TypeSpinner = (Spinner) vibrato2Pane.findViewById(R.id.vibratoTypeSpinner);
		vibrato2Amount = (SeekBar) vibrato2Pane.findViewById(R.id.vibratoAmount);
		vibrato2Rate = (SeekBar) vibrato2Pane.findViewById(R.id.vibratoRate);
		vibrato2SyncCheckBox = (CheckBox) vibrato2Pane.findViewById(R.id.vibratoSyncCheckBox);
		vibrato2Attack = (SeekBar) vibrato2Pane.findViewById(R.id.vibratoAttack);
		vibrato2Decay = (SeekBar) vibrato2Pane.findViewById(R.id.vibratoDecay);

		echoAmount = (SeekBar) echoPane.findViewById(R.id.echoAmount);
		echoDelay = (SeekBar) echoPane.findViewById(R.id.echoDelay);
		echoFeedback = (SeekBar) echoPane.findViewById(R.id.echoFeedback);
		flangeAmount = (SeekBar) echoPane.findViewById(R.id.flangeAmount);
		flangeRate = (SeekBar) echoPane.findViewById(R.id.flangeRate);
		distortionAmount = (SeekBar) echoPane.findViewById(R.id.distortionAmount);
		bitCrush = (SeekBar) echoPane.findViewById(R.id.bitCrush);
		whiteNoiseCheckBox = (CheckBox) echoPane.findViewById(R.id.whiteNoiseCheckBox);
		pinkNoiseAmount = (SeekBar) echoPane.findViewById(R.id.pinkNoiseAmount);
		brownNoiseAmount = (SeekBar) echoPane.findViewById(R.id.brownNoiseAmount);
		staticNoiseAmount = (SeekBar) echoPane.findViewById(R.id.staticNoiseAmount);
		
		if (newEffectsPane != null) {
			reverbAmount = (SeekBar) newEffectsPane.findViewById(R.id.reverbAmount);
			limiterAmount = (SeekBar) newEffectsPane.findViewById(R.id.limiterAmount);
			compressorAmount = (SeekBar) newEffectsPane.findViewById(R.id.compressorAmount);
			reverbRoomSize = (SeekBar) newEffectsPane.findViewById(R.id.reverbRoomSize);
			reverbDamping = (SeekBar) newEffectsPane.findViewById(R.id.reverbDamping);
			limiterThreshold = (SeekBar) newEffectsPane.findViewById(R.id.limiterThreshold);
			limiterRelease = (SeekBar) newEffectsPane.findViewById(R.id.limiterRelease);
			compressorRatio = (SeekBar) newEffectsPane.findViewById(R.id.compressorRatio);
			compressorAttack = (SeekBar) newEffectsPane.findViewById(R.id.compressorAttack);
		}

		sequence1 = (SeekBar) sequencerPane.findViewById(R.id.sequence1);
		sequence2 = (SeekBar) sequencerPane.findViewById(R.id.sequence2);
		sequence3 = (SeekBar) sequencerPane.findViewById(R.id.sequence3);
		sequence4 = (SeekBar) sequencerPane.findViewById(R.id.sequence4);
		sequence5 = (SeekBar) sequencerPane.findViewById(R.id.sequence5);
		sequence6 = (SeekBar) sequencerPane.findViewById(R.id.sequence6);
		sequence7 = (SeekBar) sequencerPane.findViewById(R.id.sequence7);
		sequence8 = (SeekBar) sequencerPane.findViewById(R.id.sequence8);
		sequence9 = (SeekBar) sequencerPane.findViewById(R.id.sequence9);
		sequence10 = (SeekBar) sequencerPane.findViewById(R.id.sequence10);
		sequence11 = (SeekBar) sequencerPane.findViewById(R.id.sequence11);
		sequence12 = (SeekBar) sequencerPane.findViewById(R.id.sequence12);
		sequence13 = (SeekBar) sequencerPane.findViewById(R.id.sequence13);
		sequence14 = (SeekBar) sequencerPane.findViewById(R.id.sequence14);
		sequence15 = (SeekBar) sequencerPane.findViewById(R.id.sequence15);
		sequence16 = (SeekBar) sequencerPane.findViewById(R.id.sequence16);
		// sequenceRate = (SeekBar)
		// sequencerPane.findViewById(R.id.sequenceRate);
		sequenceRateTextView = (TextView) sequencerPane.findViewById(R.id.sequenceRateTextView);
		sequenceRateUpButton = (Button) sequencerPane.findViewById(R.id.sequenceRateUpButton);
		sequenceRateDownButton = (Button) sequencerPane.findViewById(R.id.sequenceRateDownButton);
		sequenceLoopCheckBox = (CheckBox) sequencerPane.findViewById(R.id.sequenceLoopCheckBox);

		recordTime = (TextView) recordPane.findViewById(R.id.recordTime);
		recordStartButton = (Button) recordPane.findViewById(R.id.recordStartButton);
		recordStopButton = (Button) recordPane.findViewById(R.id.recordStopButton);
		recordPlayButton = (Button) recordPane.findViewById(R.id.recordPlayButton);
		recordSaveButton = (Button) recordPane.findViewById(R.id.recordSaveButton);

		keyboard = keyboardPane.findViewById(R.id.keyboard);
		key1 = (Button) keyboardPane.findViewById(R.id.key1);
		key2 = (Button) keyboardPane.findViewById(R.id.key2);
		key3 = (Button) keyboardPane.findViewById(R.id.key3);
		key4 = (Button) keyboardPane.findViewById(R.id.key4);
		key5 = (Button) keyboardPane.findViewById(R.id.key5);
		key6 = (Button) keyboardPane.findViewById(R.id.key6);
		key7 = (Button) keyboardPane.findViewById(R.id.key7);
		key8 = (Button) keyboardPane.findViewById(R.id.key8);
		key9 = (Button) keyboardPane.findViewById(R.id.key9);
		key10 = (Button) keyboardPane.findViewById(R.id.key10);
		key11 = (Button) keyboardPane.findViewById(R.id.key11);
		key12 = (Button) keyboardPane.findViewById(R.id.key12);
		key13 = (Button) keyboardPane.findViewById(R.id.key13);
		key14 = (Button) keyboardPane.findViewById(R.id.key14);
		key15 = (Button) keyboardPane.findViewById(R.id.key15);
		key16 = (Button) keyboardPane.findViewById(R.id.key16);
		key17 = (Button) keyboardPane.findViewById(R.id.key17);
		key18 = (Button) keyboardPane.findViewById(R.id.key18);
		key19 = (Button) keyboardPane.findViewById(R.id.key19);
		key20 = (Button) keyboardPane.findViewById(R.id.key20);
		key21 = (Button) keyboardPane.findViewById(R.id.key21);
		key22 = (Button) keyboardPane.findViewById(R.id.key22);
		key23 = (Button) keyboardPane.findViewById(R.id.key23);
		key24 = (Button) keyboardPane.findViewById(R.id.key24);
		key25 = (Button) keyboardPane.findViewById(R.id.key25);
		key26 = (Button) keyboardPane.findViewById(R.id.key26);
		key27 = (Button) keyboardPane.findViewById(R.id.key27);
		key28 = (Button) keyboardPane.findViewById(R.id.key28);
		key29 = (Button) keyboardPane.findViewById(R.id.key29);
		key30 = (Button) keyboardPane.findViewById(R.id.key30);
		key31 = (Button) keyboardPane.findViewById(R.id.key31);
		key32 = (Button) keyboardPane.findViewById(R.id.key32);

		// in order to avoid accidentally "pressing" the keyboard, add a click to panes
		mainLayout.setClickable(true);
		soundPane.setClickable(true);
		harmonicsPane.setClickable(true);
		filterPane.setClickable(true);
		ampPane.setClickable(true);
		vibratoPane.setClickable(true);
		vibrato2Pane.setClickable(true);
		echoPane.setClickable(true);
		effectsPane.setClickable(true);
		sequencerPane.setClickable(true);
		recordPane.setClickable(true);
		keyboardPane.setClickable(true);
		OnTouchListener touchListener = new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		};
		mainLayout.setOnTouchListener(touchListener);
		soundPane.setOnTouchListener(touchListener);
		harmonicsPane.setOnTouchListener(touchListener);
		filterPane.setOnTouchListener(touchListener);
		ampPane.setOnTouchListener(touchListener);
		vibratoPane.setOnTouchListener(touchListener);
		vibrato2Pane.setOnTouchListener(touchListener);
		echoPane.setOnTouchListener(touchListener);
		effectsPane.setOnTouchListener(touchListener);
		sequencerPane.setOnTouchListener(touchListener);
		recordPane.setOnTouchListener(touchListener);
		keyboardPane.setOnTouchListener(touchListener);

		monoRadio.setOnCheckedChangeListener(this);
		polyRadio.setOnCheckedChangeListener(this);
		chorusRadio.setOnCheckedChangeListener(this);

		ArrayAdapter<CharSequence> velocityAdapter = com.gallantrealm.android.Translator.getArrayAdapter(this, R.layout.spinner_item, new String[] { "None", "Volume", "Filter", "Both" });
		velocityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		velocitySpinner.setAdapter(velocityAdapter);
		velocitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView av, View v, int arg2, long arg3) {
				int velocitySelection = velocitySpinner.getSelectedItemPosition();
				if (velocitySelection == 0) { // none
					instrument.velocityType = WaveSynth.VELOCITY_NONE;
				} else if (velocitySelection == 1) { // volume
					instrument.velocityType = WaveSynth.VELOCITY_VOLUME;
				} else if (velocitySelection == 2) { // filter
					instrument.velocityType = WaveSynth.VELOCITY_FILTER;
				} else if (velocitySelection == 3) { // both
					instrument.velocityType = WaveSynth.VELOCITY_BOTH;
				}
				instrument.updateParams();
			}

			@Override
			public void onNothingSelected(AdapterView av) {
			}
		});

		ArrayAdapter<CharSequence> expressionAdapter = com.gallantrealm.android.Translator.getArrayAdapter(this, R.layout.spinner_item, new String[] { "None", "Volume", "Filter", "Vibrato", "Pitch", "Volume+Filter" });
		expressionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		expressionSpinner.setAdapter(expressionAdapter);
		expressionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView av, View v, int arg2, long arg3) {
				int expressionSelection = expressionSpinner.getSelectedItemPosition();
				if (expressionSelection == 0) { // none
					instrument.expressionType = WaveSynth.EXPRESSION_NONE;
				} else if (expressionSelection == 1) { // volume
					instrument.expressionType = WaveSynth.EXPRESSION_VOLUME;
				} else if (expressionSelection == 2) { // filter
					instrument.expressionType = WaveSynth.EXPRESSION_FILTER;
				} else if (expressionSelection == 3) { // vibrato
					instrument.expressionType = WaveSynth.EXPRESSION_VIBRATO;
				} else if (expressionSelection == 4) { // pitch
					instrument.expressionType = WaveSynth.EXPRESSION_PITCH;
				} else if (expressionSelection == 5) { // both
					instrument.expressionType = WaveSynth.EXPRESSION_VOLUME_AND_FILTER;
				}
				instrument.updateParams();
			}

			@Override
			public void onNothingSelected(AdapterView av) {
			}
		});

		holdButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				instrument.hold = isChecked;
				instrument.keyRelease(1);
				instrument.keyRelease(2);
				instrument.keyRelease(3);
				instrument.keyRelease(4);
				clearHolds();
			}
		});

		ArrayAdapter<CharSequence> keyAdapter = com.gallantrealm.android.Translator.getArrayAdapter(this, R.layout.spinner_item, new String[] { "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B" });
		keyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		keySpinner.setAdapter(keyAdapter);
		keySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView av, View v, int arg2, long arg3) {
				int keySelection = keySpinner.getSelectedItemPosition();
				instrument.key = keySelection;
				instrument.updateParams();
			}

			@Override
			public void onNothingSelected(AdapterView av) {
			}
		});

		setTheme(clientModel.getBackgroundName(), clientModel.getCustomBackgroundPath());

		// Create CPU overlay
		cpuOverlay = new TextView(this);
		cpuOverlay.setTextColor(0xFFFFFFFF);
		cpuOverlay.setTextSize(12);
		cpuOverlay.setBackgroundColor(0x80000000);
		cpuOverlay.setPadding(8, 4, 8, 4);
		android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(
			android.widget.FrameLayout.LayoutParams.WRAP_CONTENT,
			android.widget.FrameLayout.LayoutParams.WRAP_CONTENT
		);
		params.gravity = android.view.Gravity.TOP | android.view.Gravity.RIGHT;
		params.rightMargin = 10;
		params.topMargin = 10;
		cpuOverlay.setLayoutParams(params);
		((android.view.ViewGroup) mainLayout.getParent()).addView(cpuOverlay);
		updateCpuOverlay();

		ArrayAdapter<CharSequence> filterTypeAdapter = com.gallantrealm.android.Translator.getArrayAdapter(this, R.layout.spinner_item,
				new String[] { "Low Pass", "Band Pass", "High Pass", "Fade", "Comb 1", "Comb 2", "Comb 3", "Comb 4", "Formant 1", "Formant 2", "Formant 3", "Formant 4" });
		filterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		filterTypeSpinner.setAdapter(filterTypeAdapter);

		ArrayAdapter<CharSequence> vibratoDestinationAdapter = com.gallantrealm.android.Translator.getArrayAdapter(this, R.layout.spinner_item, new String[] { "Pitch", "Filter", "Amp" });
		vibratoDestinationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		vibratoDestinationSpinner.setAdapter(vibratoDestinationAdapter);
		vibratoDestinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView av, View v, int arg2, long arg3) {
				instrument.vibratoDestination = vibratoDestinationSpinner.getSelectedItemPosition();
				instrument.updateParams();
			}

			@Override
			public void onNothingSelected(AdapterView av) {
			}

		});

		ArrayAdapter<CharSequence> vibratoTypeAdapter = com.gallantrealm.android.Translator.getArrayAdapter(this, R.layout.spinner_item, new String[] { "Wave", "Ramp Up", "Ramp Down", "Two Tone", "Noise", "Pulse 1/4", "Pulse 1/8", "Pulse 1/16" });
		vibratoTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		vibratoTypeSpinner.setAdapter(vibratoTypeAdapter);
		vibratoTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView av, View v, int arg2, long arg3) {
				instrument.vibratoType = vibratoTypeSpinner.getSelectedItemPosition();
				instrument.genVibratoWaves();
				instrument.updateParams();
			}

			@Override
			public void onNothingSelected(AdapterView av) {
			}

		});

		ArrayAdapter<CharSequence> vibrato2DestinationAdapter = com.gallantrealm.android.Translator.getArrayAdapter(this, R.layout.spinner_item, new String[] { "Pitch", "Filter", "Amp", "Mod Pitch", "Mod Amp" });
		vibrato2DestinationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		vibrato2DestinationSpinner.setAdapter(vibrato2DestinationAdapter);
		vibrato2DestinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView av, View v, int arg2, long arg3) {
				instrument.vibrato2Destination = vibrato2DestinationSpinner.getSelectedItemPosition();
				instrument.updateParams();
			}

			@Override
			public void onNothingSelected(AdapterView av) {
			}

		});

		ArrayAdapter<CharSequence> vibrato2TypeAdapter = com.gallantrealm.android.Translator.getArrayAdapter(this, R.layout.spinner_item, new String[] { "Wave", "Ramp Up", "Ramp Down", "Two Tone", "Noise", "Pulse 1/4", "Pulse 1/8", "Pulse 1/16" });
		vibrato2TypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		vibrato2TypeSpinner.setAdapter(vibrato2TypeAdapter);
		vibrato2TypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView av, View v, int arg2, long arg3) {
				instrument.vibrato2Type = vibrato2TypeSpinner.getSelectedItemPosition();
				instrument.genVibratoWaves();
				instrument.updateParams();
			}

			@Override
			public void onNothingSelected(AdapterView av) {
			}

		});

		soundButton.setSelected(true);
		harmonicsButton.setSelected(false);
		filterButton.setSelected(false);
		ampButton.setSelected(false);
		vibratoButton.setSelected(false);
		vibrato2Button.setSelected(false);
		echoButton.setSelected(false);
		if (newEffectsButton != null) {
			newEffectsButton.setSelected(false);
		}
		sequencerButton.setSelected(false);
		recordButton.setSelected(false);
		soundPane.setVisibility(View.VISIBLE);
		harmonicsPane.setVisibility(View.GONE);
		filterPane.setVisibility(View.GONE);
		ampPane.setVisibility(View.GONE);
		vibratoPane.setVisibility(View.GONE);
		vibrato2Pane.setVisibility(View.GONE);
		echoPane.setVisibility(View.GONE);
		if (newEffectsPane != null) {
			newEffectsPane.setVisibility(View.GONE);
		}
		sequencerPane.setVisibility(View.GONE);
		recordPane.setVisibility(View.GONE);
		soundPane.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		harmonicsPane.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		filterPane.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		ampPane.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		vibratoPane.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		vibrato2Pane.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		echoPane.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		effectsPane.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		sequencerPane.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		recordPane.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

//		if (!clientModel.isFullVersion() && !clientModel.isGoggleDogPass()) {     	// now 100% free
//			buyButton.setVisibility(View.VISIBLE);
//		} else {
			buyButton.setVisibility(View.GONE);
//		}
		sendButton.setVisibility(View.GONE);

		buyButton.setOnClickListener(this);
		soundButton.setOnClickListener(this);
		harmonicsButton.setOnClickListener(this);
		filterButton.setOnClickListener(this);
		ampButton.setOnClickListener(this);
		vibratoButton.setOnClickListener(this);
		vibrato2Button.setOnClickListener(this);
		echoButton.setOnClickListener(this);
		Button effectsButton = (Button) findViewById(R.id.effectsButton);
		effectsButton.setOnClickListener(this);
		if (newEffectsButton != null) {
			newEffectsButton.setOnClickListener(this);
		}
		sequencerButton.setOnClickListener(this);
		recordButton.setOnClickListener(this);
		recordStartButton.setOnClickListener(this);
		recordStopButton.setOnClickListener(this);
		recordPlayButton.setOnClickListener(this);
		recordSaveButton.setOnClickListener(this);
		settingsButton.setOnClickListener(this);

		// initialize the sound spinner
		updateSoundSpinner();
		// setup the selection listener
		soundSpinner.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					showCategorizedInstrumentDialog();
					return true;
				}
				return false;
			}
		});
		// select the sound from last session, or the first sound
		String soundName = clientModel.getInstrumentName();
		int soundPosition = 0;
		for (int i = 0; i < soundSpinner.getCount(); i++) {
			if (soundName.equals(soundSpinner.getItemAtPosition(i))) {
				soundPosition = i;
			}
		}
		soundSpinner.setSelection(soundPosition);
		// Load the initial sound
		if (soundPosition < soundSpinner.getCount()) {
			loadSound((String) soundSpinner.getItemAtPosition(soundPosition));
		}

		if (clientModel.getPlayCount() <= 1) {
			// Choose a good default keyboard size
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			double screenSize = Math.sqrt(Math.pow(metrics.heightPixels / metrics.ydpi, 2) + Math.pow(metrics.widthPixels / metrics.xdpi, 2));
			System.out.println("ScreenSize is " + screenSize);
			if (screenSize >= 9.5) {
				clientModel.setKeyboardSize(3);
			} else if (screenSize >= 6.5) {
				clientModel.setKeyboardSize(2);
			} else if (screenSize >= 4.5) {
				clientModel.setKeyboardSize(1);
			} else {
				clientModel.setKeyboardSize(0);
			}
		}
		setKeyboardSize(clientModel.getKeyboardSize());

		setMidiChannel(clientModel.getMidiChannel());

		instrument.tuning = clientModel.getSampleRateReducer();

		instrument.tuningCents = clientModel.getTuningCents();

		saveButton.setOnClickListener(this);
		sendButton.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		chorusWidth.setOnSeekBarChangeListener(this);
		octave2Radio.setOnCheckedChangeListener(this);
		octave3Radio.setOnCheckedChangeListener(this);
		octave4Radio.setOnCheckedChangeListener(this);
		octave5Radio.setOnCheckedChangeListener(this);
		octave6Radio.setOnCheckedChangeListener(this);

		harmonic1.setOnSeekBarChangeListener(this);
		harmonic2.setOnSeekBarChangeListener(this);
		harmonic3.setOnSeekBarChangeListener(this);
		harmonic4.setOnSeekBarChangeListener(this);
		harmonic5.setOnSeekBarChangeListener(this);
		harmonic6.setOnSeekBarChangeListener(this);
		harmonic7.setOnSeekBarChangeListener(this);
		harmonic8.setOnSeekBarChangeListener(this);
		harmonic9.setOnSeekBarChangeListener(this);
		harmonic10.setOnSeekBarChangeListener(this);
		harmonic11.setOnSeekBarChangeListener(this);
		harmonic12.setOnSeekBarChangeListener(this);
		harmonic13.setOnSeekBarChangeListener(this);
		harmonic14.setOnSeekBarChangeListener(this);
		harmonic15.setOnSeekBarChangeListener(this);
		harmonic16.setOnSeekBarChangeListener(this);
		harmonic17.setOnSeekBarChangeListener(this);
		harmonic18.setOnSeekBarChangeListener(this);
		harmonic19.setOnSeekBarChangeListener(this);
		harmonic20.setOnSeekBarChangeListener(this);
		harmonic21.setOnSeekBarChangeListener(this);
		harmonic22.setOnSeekBarChangeListener(this);
		harmonic23.setOnSeekBarChangeListener(this);
		harmonic24.setOnSeekBarChangeListener(this);
		harmonic25.setOnSeekBarChangeListener(this);
		harmonic26.setOnSeekBarChangeListener(this);
		harmonic27.setOnSeekBarChangeListener(this);
		harmonic28.setOnSeekBarChangeListener(this);
		harmonic29.setOnSeekBarChangeListener(this);
		harmonic30.setOnSeekBarChangeListener(this);
		harmonic31.setOnSeekBarChangeListener(this);
		harmonic32.setOnSeekBarChangeListener(this);
		filterTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView av, View v, int arg2, long arg3) {
				if (filterTypeSpinner.getSelectedItem().equals("Low Pass")) {
					instrument.filterType = instrument.FILTER_LOWPASS;
					instrument.filterFixed = false;
				} else if (filterTypeSpinner.getSelectedItem().equals("Band Pass")) {
					instrument.filterType = instrument.FILTER_BANDPASS;
					instrument.filterFixed = false;
				} else if (filterTypeSpinner.getSelectedItem().equals("Fade")) {
					instrument.filterType = instrument.FILTER_FADE;
					instrument.filterFixed = false;
				} else if (filterTypeSpinner.getSelectedItem().equals("High Pass")) {
					instrument.filterType = instrument.FILTER_HIGHPASS;
					instrument.filterFixed = false;
				} else if (filterTypeSpinner.getSelectedItem().equals("Comb 1")) {
					instrument.filterType = instrument.FILTER_COMB1;
					instrument.filterFixed = false;
				} else if (filterTypeSpinner.getSelectedItem().equals("Comb 2")) {
					instrument.filterType = instrument.FILTER_COMB2;
					instrument.filterFixed = false;
				} else if (filterTypeSpinner.getSelectedItem().equals("Comb 3")) {
					instrument.filterType = instrument.FILTER_COMB3;
					instrument.filterFixed = false;
				} else if (filterTypeSpinner.getSelectedItem().equals("Comb 4")) {
					instrument.filterType = instrument.FILTER_COMB4;
					instrument.filterFixed = false;
				} else if (filterTypeSpinner.getSelectedItem().equals("Formant 1")) {
					instrument.filterType = instrument.FILTER_FORMANT1;
					instrument.filterFixed = true;
				} else if (filterTypeSpinner.getSelectedItem().equals("Formant 2")) {
					instrument.filterType = instrument.FILTER_FORMANT2;
					instrument.filterFixed = true;
				} else if (filterTypeSpinner.getSelectedItem().equals("Formant 3")) {
					instrument.filterType = instrument.FILTER_FORMANT3;
					instrument.filterFixed = true;
				} else if (filterTypeSpinner.getSelectedItem().equals("Formant 4")) {
					instrument.filterType = instrument.FILTER_FORMANT4;
					instrument.filterFixed = true;
				}
				instrument.genWaves();
				instrument.updateParams();
			}

			@Override
			public void onNothingSelected(AdapterView av) {
			}

		});
		filterResonance.setOnSeekBarChangeListener(this);
		filterLow.setOnSeekBarChangeListener(this);
		filterHigh.setOnSeekBarChangeListener(this);
		filterEnvAttack.setOnSeekBarChangeListener(this);
		filterEnvDecay.setOnSeekBarChangeListener(this);
		filterEnvSustain.setOnSeekBarChangeListener(this);
		filterEnvRelease.setOnSeekBarChangeListener(this);
		ampVolume.setOnSeekBarChangeListener(this);
		ampAttack.setOnSeekBarChangeListener(this);
		ampDecay.setOnSeekBarChangeListener(this);
		ampSustain.setOnSeekBarChangeListener(this);
		ampRelease.setOnSeekBarChangeListener(this);
		ampOverdrive.setOnSeekBarChangeListener(this);
		ampOverdrivePerVoiceCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				instrument.ampOverdrivePerVoice = isChecked;
				instrument.updateParams();
			}
		});
		vibratoAmount.setOnSeekBarChangeListener(this);
		vibratoRate.setOnSeekBarChangeListener(this);
		vibratoSyncCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				instrument.vibratoSync = isChecked;
				instrument.updateParams();
			}
		});
		vibratoAttack.setOnSeekBarChangeListener(this);
		vibratoDecay.setOnSeekBarChangeListener(this);
		vibrato2Amount.setOnSeekBarChangeListener(this);
		vibrato2Rate.setOnSeekBarChangeListener(this);
		vibrato2SyncCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				instrument.vibrato2Sync = isChecked;
				instrument.updateParams();
			}
		});
		vibrato2Attack.setOnSeekBarChangeListener(this);
		vibrato2Decay.setOnSeekBarChangeListener(this);
		echoAmount.setOnSeekBarChangeListener(this);
		echoDelay.setOnSeekBarChangeListener(this);
		echoFeedback.setOnSeekBarChangeListener(this);
		flangeAmount.setOnSeekBarChangeListener(this);
		flangeRate.setOnSeekBarChangeListener(this);
		distortionAmount.setOnSeekBarChangeListener(this);
		bitCrush.setOnSeekBarChangeListener(this);
		whiteNoiseCheckBox.setOnCheckedChangeListener(this);
		pinkNoiseAmount.setOnSeekBarChangeListener(this);
		brownNoiseAmount.setOnSeekBarChangeListener(this);
		staticNoiseAmount.setOnSeekBarChangeListener(this);
		reverbAmount.setOnSeekBarChangeListener(this);
		limiterAmount.setOnSeekBarChangeListener(this);
		compressorAmount.setOnSeekBarChangeListener(this);
		
		// New effects controls
		if (newEffectsPane != null) {
			reverbRoomSize.setOnSeekBarChangeListener(this);
			reverbDamping.setOnSeekBarChangeListener(this);
			limiterThreshold.setOnSeekBarChangeListener(this);
			limiterRelease.setOnSeekBarChangeListener(this);
			compressorRatio.setOnSeekBarChangeListener(this);
			compressorAttack.setOnSeekBarChangeListener(this);
		}
		
		portamentoAmount.setOnSeekBarChangeListener(this);
		sequence1.setOnSeekBarChangeListener(this);
		sequence2.setOnSeekBarChangeListener(this);
		sequence3.setOnSeekBarChangeListener(this);
		sequence4.setOnSeekBarChangeListener(this);
		sequence5.setOnSeekBarChangeListener(this);
		sequence6.setOnSeekBarChangeListener(this);
		sequence7.setOnSeekBarChangeListener(this);
		sequence8.setOnSeekBarChangeListener(this);
		sequence9.setOnSeekBarChangeListener(this);
		sequence10.setOnSeekBarChangeListener(this);
		sequence11.setOnSeekBarChangeListener(this);
		sequence12.setOnSeekBarChangeListener(this);
		sequence13.setOnSeekBarChangeListener(this);
		sequence14.setOnSeekBarChangeListener(this);
		sequence15.setOnSeekBarChangeListener(this);
		sequence16.setOnSeekBarChangeListener(this);
		// sequenceRate.setOnSeekBarChangeListener(this);
		sequenceRateUpButton.setOnTouchListener(this);
		sequenceRateDownButton.setOnTouchListener(this);
		sequenceLoopCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				instrument.sequenceLoop = isChecked;
				instrument.updateParams();
			}
		});
		keyboard.setOnTouchListener(this);

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakelock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "WaveSynth:WakeLock");

		View decorView = getWindow().getDecorView();
		decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
			@Override
			public void onSystemUiVisibilityChange(int visibility) {
				// hide system navigation
				if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
					final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
					decorView.setSystemUiVisibility( flags);
				}
			}
		});

		// In Desktop mode, a maximize of the window will change the padding.  The
		// following listener adjusts the padding to compensate
		View rootLayout = findViewById(R.id.mainLayout);
		rootLayout.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
			@Override
			public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
				// 'systemBars' includes the status bar and the desktop caption bar
				android.graphics.Insets bars = insets.getInsets(
						WindowInsets.Type.systemBars()
				);

				// Apply the top inset as padding so your app content
				// shifts down below the desktop title bar
				v.setPadding(bars.left, bars.top, bars.right, bars.bottom);

				// Return 'consume' so the insets aren't passed down to child views
				// which might cause double-padding
				return WindowInsets.CONSUMED;
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	boolean selectingCustomTheme;

	/**
	 * Needed for in-app purchase, file chooser, and other actions that launch separate activities.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// Pass on the activity result to clientModel for proper in-app billing
		// behavior
		if (clientModel.handleActivityResult(requestCode, resultCode, data)) {
			return;
		}
		
		if (selectingCustomTheme) {
			if (data != null) {
				String path = "";
				Uri mImageCaptureUri = data.getData();
				System.out.println("Selected Uri is " + mImageCaptureUri);
				path = ContentUriUtil.getPath(this, mImageCaptureUri);
				System.out.println("Image selected: " + path);
				setCustomBackground(path);
				ClientModel.getClientModel().setCustomBackgroundPath(path);
				ClientModel.getClientModel().savePreferences(this);
			}
			selectingCustomTheme = false;
		}
		
		// not handled, so let default handle
		super.onActivityResult(requestCode, resultCode, data);
	}

	@SuppressLint("NewApi")
	private void setCustomBackground(String path) {
		if (path == null || path.length() == 0) {
			return;
		}
		System.out.println("Loading custom background " + path);
		try {
			File file = new File(path);
			InputStream inStream = new BufferedInputStream(new FileInputStream(file), 65536);
			Bitmap tbitmap = BitmapFactory.decodeStream(inStream);
			inStream.close();
			Bitmap bitmap = tbitmap.copy(tbitmap.getConfig(), true);
			tbitmap.recycle();
			mainLayout.setBackground(new BitmapDrawable(bitmap));
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(MainActivity.this, "Custom background cannot be opened.", Toast.LENGTH_SHORT).show();
		}
	}

	boolean stopUpdateThread;
	UpdateThread updateThread;

	class UpdateThread extends Thread {

		boolean stopThread;;

		public void safeStop() {
			stopThread = true;
		}

		@Override
		public void run() {
			try {
				while (!stopThread) {
					if (instrument != null) {
						final int time = synth.getRecordTime();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								int mins = time / 60;
								int secs = time % 60;
								if (secs < 10) {
									recordTime.setText("" + mins + ":0" + secs);
								} else {
									recordTime.setText("" + mins + ":" + secs);
								}
							}
						});
						Thread.sleep(250);
					}
				}
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (v == buyButton) {
//			if (!clientModel.isFullVersion() && !clientModel.isGoggleDogPass() && Locale.getDefault().getLanguage().startsWith("en")) {
//				final MessageDialog fullVersionPrompt = new MessageDialog(this, "Full Version", "Requires full version.", new String[] { "Buy", "Cancel" });
//				fullVersionPrompt.setOnDismissListener(new DialogInterface.OnDismissListener() {
//					@Override
//					public void onDismiss(DialogInterface dialog) {
//						if (fullVersionPrompt.getButtonPressed() == 0) {
							clientModel.buyFullVersion();
//						}
//					}
//				});
//				fullVersionPrompt.show();
//				return;
//			}
		} else if (v == saveButton) {
			saveSound();
		} else if (v == sendButton) {
			sendSound();
		} else if (v == deleteButton) {
			deleteSound();
		} else if (v == recordStartButton) {
			startRecording();
		} else if (v == recordStopButton) {
			stopRecording();
		} else if (v == recordPlayButton) {
			playbackRecording();
		} else if (v == recordSaveButton) {
			saveRecording();
		} else if (v == settingsButton) {
			final SettingsDialog settingsDialog = new SettingsDialog(this);
			settingsDialog.show();
		} else {
			soundPane.setVisibility(View.GONE);
			harmonicsPane.setVisibility(View.GONE);
			filterPane.setVisibility(View.GONE);
			ampPane.setVisibility(View.GONE);
			vibratoPane.setVisibility(View.GONE);
			vibrato2Pane.setVisibility(View.GONE);
			echoPane.setVisibility(View.GONE);
			if (newEffectsPane != null) {
				newEffectsPane.setVisibility(View.GONE);
			}
			sequencerPane.setVisibility(View.GONE);
			recordPane.setVisibility(View.GONE);
			soundButton.setSelected(false);
			harmonicsButton.setSelected(false);
			filterButton.setSelected(false);
			ampButton.setSelected(false);
			vibratoButton.setSelected(false);
			vibrato2Button.setSelected(false);
			echoButton.setSelected(false);
			Button effectsButton = (Button) findViewById(R.id.effectsButton);
			effectsButton.setSelected(false);
			if (newEffectsButton != null) {
				newEffectsButton.setSelected(false);
			}
			sequencerButton.setSelected(false);
			recordButton.setSelected(false);
			if (v == soundButton) {
				soundPane.setVisibility(View.VISIBLE);
				soundButton.setSelected(true);
			} else if (v == harmonicsButton) {
				harmonicsPane.setVisibility(View.VISIBLE);
				harmonicsButton.setSelected(true);
			} else if (v == filterButton) {
				filterPane.setVisibility(View.VISIBLE);
				filterButton.setSelected(true);
			} else if (v == ampButton) {
				ampPane.setVisibility(View.VISIBLE);
				ampButton.setSelected(true);
			} else if (v == vibratoButton) {
				vibratoPane.setVisibility(View.VISIBLE);
				vibratoButton.setSelected(true);
			} else if (v == vibrato2Button) {
				vibrato2Pane.setVisibility(View.VISIBLE);
				vibrato2Button.setSelected(true);
			} else if (v == echoButton) {
				echoPane.setVisibility(View.VISIBLE);
				echoButton.setSelected(true);
			} else if (v == effectsButton) {
				effectsPane.setVisibility(View.VISIBLE);
				effectsButton.setSelected(true);
			} else if (newEffectsButton != null && v == newEffectsButton) {
				if (newEffectsPane != null) {
					newEffectsPane.setVisibility(View.VISIBLE);
				}
				newEffectsButton.setSelected(true);
			} else if (v == sequencerButton) {
				sequencerPane.setVisibility(View.VISIBLE);
				sequencerButton.setSelected(true);
			} else if (v == recordButton) {
				recordPane.setVisibility(View.VISIBLE);
				recordButton.setSelected(true);
			}
		}
	}

	public void setTheme(String option, String customImagePath) {
		// Handle custom background
		if (option != null && option.equals("Custom")) {
			if (customImagePath != null) {
				setCustomBackground(customImagePath);
			} else {
				loadBackgroundFromAssets("Onyx");
				selectingCustomTheme = true;
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Complete action using"), 0);
			}
		} else {
			// Load background from assets/backgrounds folder
			String backgroundName = (option == null) ? "Onyx" : option;
			loadBackgroundFromAssets(backgroundName);
		}
	}
	
	private void loadBackgroundFromAssets(String name) {
		try {
			// Try with .png extension first
			java.io.InputStream is = getAssets().open("backgrounds/" + name + ".png");
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			is.close();
			mainLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
		} catch (java.io.IOException e) {
			try {
				// Try with .jpg extension
				java.io.InputStream is = getAssets().open("backgrounds/" + name + ".jpg");
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				is.close();
				mainLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
			} catch (java.io.IOException ex) {
				// Fallback to default if not found
				mainLayout.setBackgroundResource(R.raw.onyx_background);
			}
		}
	}

	public void setKeyboardSize(int keysSelection) {
		if (keysSelection == 0) { // 13
			key14.setVisibility(View.GONE);
			key15.setVisibility(View.GONE);
			key16.setVisibility(View.GONE);
			keyboardPane.findViewById(R.id.key16spacer).setVisibility(View.GONE);
			key17.setVisibility(View.GONE);
			key18.setVisibility(View.GONE);
			key19.setVisibility(View.GONE);
			key20.setVisibility(View.GONE);
			key21.setVisibility(View.GONE);
			key22.setVisibility(View.GONE);
			key23.setVisibility(View.GONE);
			keyboardPane.findViewById(R.id.key23spacer).setVisibility(View.GONE);
			key24.setVisibility(View.GONE);
			key25.setVisibility(View.GONE);
			key26.setVisibility(View.GONE);
			key27.setVisibility(View.GONE);
			key28.setVisibility(View.GONE);
			keyboardPane.findViewById(R.id.key28spacer).setVisibility(View.GONE);
			key29.setVisibility(View.GONE);
			key30.setVisibility(View.GONE);
			key31.setVisibility(View.GONE);
			key32.setVisibility(View.GONE);
		} else if (keysSelection == 1) { // 20
			key14.setVisibility(View.VISIBLE);
			key15.setVisibility(View.VISIBLE);
			key16.setVisibility(View.VISIBLE);
			keyboardPane.findViewById(R.id.key16spacer).setVisibility(View.VISIBLE);
			key17.setVisibility(View.VISIBLE);
			key18.setVisibility(View.VISIBLE);
			key19.setVisibility(View.VISIBLE);
			key20.setVisibility(View.VISIBLE);
			key21.setVisibility(View.GONE);
			key22.setVisibility(View.GONE);
			key23.setVisibility(View.GONE);
			keyboardPane.findViewById(R.id.key23spacer).setVisibility(View.GONE);
			key24.setVisibility(View.GONE);
			key25.setVisibility(View.GONE);
			key26.setVisibility(View.GONE);
			key27.setVisibility(View.GONE);
			key28.setVisibility(View.GONE);
			keyboardPane.findViewById(R.id.key28spacer).setVisibility(View.GONE);
			key29.setVisibility(View.GONE);
			key30.setVisibility(View.GONE);
			key31.setVisibility(View.GONE);
			key32.setVisibility(View.GONE);
		} else if (keysSelection == 2) { // 25
			key14.setVisibility(View.VISIBLE);
			key15.setVisibility(View.VISIBLE);
			key16.setVisibility(View.VISIBLE);
			keyboardPane.findViewById(R.id.key16spacer).setVisibility(View.VISIBLE);
			key17.setVisibility(View.VISIBLE);
			key18.setVisibility(View.VISIBLE);
			key19.setVisibility(View.VISIBLE);
			key20.setVisibility(View.VISIBLE);
			key21.setVisibility(View.VISIBLE);
			key22.setVisibility(View.VISIBLE);
			key23.setVisibility(View.VISIBLE);
			keyboardPane.findViewById(R.id.key23spacer).setVisibility(View.VISIBLE);
			key24.setVisibility(View.VISIBLE);
			key25.setVisibility(View.VISIBLE);
			key26.setVisibility(View.GONE);
			key27.setVisibility(View.GONE);
			key28.setVisibility(View.GONE);
			keyboardPane.findViewById(R.id.key28spacer).setVisibility(View.GONE);
			key29.setVisibility(View.GONE);
			key30.setVisibility(View.GONE);
			key31.setVisibility(View.GONE);
			key32.setVisibility(View.GONE);
		} else if (keysSelection == 3) { // 32
			key14.setVisibility(View.VISIBLE);
			key15.setVisibility(View.VISIBLE);
			key16.setVisibility(View.VISIBLE);
			keyboardPane.findViewById(R.id.key16spacer).setVisibility(View.VISIBLE);
			key17.setVisibility(View.VISIBLE);
			key18.setVisibility(View.VISIBLE);
			key19.setVisibility(View.VISIBLE);
			key20.setVisibility(View.VISIBLE);
			key21.setVisibility(View.VISIBLE);
			key22.setVisibility(View.VISIBLE);
			key23.setVisibility(View.VISIBLE);
			keyboardPane.findViewById(R.id.key23spacer).setVisibility(View.VISIBLE);
			key24.setVisibility(View.VISIBLE);
			key25.setVisibility(View.VISIBLE);
			key26.setVisibility(View.VISIBLE);
			key27.setVisibility(View.VISIBLE);
			key28.setVisibility(View.VISIBLE);
			keyboardPane.findViewById(R.id.key28spacer).setVisibility(View.VISIBLE);
			key29.setVisibility(View.VISIBLE);
			key30.setVisibility(View.VISIBLE);
			key31.setVisibility(View.VISIBLE);
			key32.setVisibility(View.VISIBLE);
		}
	}

	public void setMidiChannel(int midiChannel) {
		this.myMidiChannel = midiChannel;
		midi.setMidiChannel(midiChannel);
	}

	public void setTuning(int tuning) {
		instrument.tuning = tuning;
		instrument.updateParams();
	}

	public void setTuningCents(int cents) {
		instrument.tuningCents = cents;
		instrument.updateParams();
	}

	// Inner class to hold instrument metadata
	private static class InstrumentInfo {
		String name;
		String displayName;
		String filename;
		String category;
		String type;
		long sizeBytes;
		String author;
		
		InstrumentInfo(String name, String filename, String category) {
			this.name = name;
			this.filename = filename;
			this.category = category;
			this.displayName = formatInstrumentName(name);
			this.author = "";
			if (filename.endsWith(".wavesynth")) {
				this.type = "WaveSynthPlus";
			} else if (filename.endsWith(".easysynth")) {
				this.type = "EasySynth";
			} else {
				this.type = "Unknown";
			}
		}
	}
	
	private static String formatInstrumentName(String name) {
		// Convert camelCase or PascalCase to Title Case with spaces
		StringBuilder formatted = new StringBuilder();
		boolean lastWasUpper = false;
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (i == 0) {
				formatted.append(Character.toUpperCase(c));
				lastWasUpper = Character.isUpperCase(c);
			} else if (Character.isUpperCase(c) && !lastWasUpper) {
				formatted.append(' ').append(c);
				lastWasUpper = true;
			} else {
				formatted.append(c);
				lastWasUpper = Character.isUpperCase(c);
			}
		}
		return formatted.toString();
	}

	private void showCategorizedInstrumentDialog() {
		final android.app.Dialog dialog = new android.app.Dialog(this, R.style.Theme_Dialog);
		dialog.setContentView(R.layout.instrument_category_dialog);
		dialog.setTitle("Choose Instrument");
		dialog.setCanceledOnTouchOutside(true);
		
		// Make dialog wider
		if (dialog.getWindow() != null) {
			android.view.WindowManager.LayoutParams lp = new android.view.WindowManager.LayoutParams();
			lp.copyFrom(dialog.getWindow().getAttributes());
			lp.width = (int)(getResources().getDisplayMetrics().widthPixels * 0.85);
			lp.height = (int)(getResources().getDisplayMetrics().heightPixels * 0.75);
			dialog.getWindow().setAttributes(lp);
		}
		
		final LinearLayout categoryContainer = dialog.findViewById(R.id.categoryButtonsContainer);
		final ListView instrumentList = dialog.findViewById(R.id.instrumentList);
		
		// Get all categories with metadata
		final java.util.Map<String, java.util.ArrayList<InstrumentInfo>> categoryMap = new java.util.LinkedHashMap<>();
		try {
			String[] categories = getAssets().list("instruments");
			if (categories != null) {
				java.util.Arrays.sort(categories);
				for (String category : categories) {
					java.util.ArrayList<InstrumentInfo> instruments = new java.util.ArrayList<>();
					String[] files = getAssets().list("instruments/" + category);
					if (files != null) {
						for (String filename : files) {
							if (filename.endsWith(".easysynth") || filename.endsWith(".wavesynth")) {
								String name = filename.substring(0, filename.lastIndexOf('.'));
								InstrumentInfo info = new InstrumentInfo(name, filename, category);
								// Get file size from assets
								try {
									java.io.InputStream is = getAssets().open("instruments/" + category + "/" + filename);
									info.sizeBytes = is.available();
									is.close();
								} catch (Exception e) {
									info.sizeBytes = 0;
								}
								// Try to load author from .wavesynth files
								if (filename.endsWith(".wavesynth")) {
									try {
										Sound sound = (Sound) clientModel.loadObject("instruments/" + category + "/" + filename, false);
										if (sound != null && sound.author != null && !sound.author.isEmpty()) {
											info.author = sound.author;
										}
									} catch (Exception e) {
										// Ignore if unable to load
									}
								}
								instruments.add(info);
							}
						}
					}
					if (!instruments.isEmpty()) {
						java.util.Collections.sort(instruments, new java.util.Comparator<InstrumentInfo>() {
							@Override
							public int compare(InstrumentInfo a, InstrumentInfo b) {
								return a.name.compareTo(b.name);
							}
						});
						categoryMap.put(category, instruments);
					}
				}
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		
		// Also add saved instruments from filesDir
		File filesDir = getFilesDir();
		File[] categoryDirs = filesDir.listFiles();
		if (categoryDirs != null) {
			for (File catDir : categoryDirs) {
				if (catDir.isDirectory()) {
					String category = catDir.getName();
					java.util.ArrayList<InstrumentInfo> instruments = categoryMap.get(category);
					if (instruments == null) {
						instruments = new java.util.ArrayList<>();
						categoryMap.put(category, instruments);
					}
					
					File[] catFiles = catDir.listFiles();
					if (catFiles != null) {
						for (File file : catFiles) {
							String filename = file.getName();
							if (filename.endsWith(".wavesynth") || filename.endsWith(".easysynth")) {
								int dotPos = filename.lastIndexOf('.');
								if (dotPos > 0) {
									String name = filename.substring(0, dotPos);
									InstrumentInfo info = new InstrumentInfo(name, filename, category);
									info.sizeBytes = file.length();
									// Load author from saved file
									if (filename.endsWith(".wavesynth")) {
										try {
											Sound sound = (Sound) clientModel.loadObject(category + "/" + name + ".wavesynth", true);
											if (sound != null && sound.author != null && !sound.author.isEmpty()) {
												info.author = sound.author;
											}
										} catch (Exception e) {
											// Ignore if unable to load
										}
									}
									instruments.add(info);
								}
							}
						}
					}
					
					// Sort instruments in this category
					if (!instruments.isEmpty()) {
						java.util.Collections.sort(instruments, new java.util.Comparator<InstrumentInfo>() {
							@Override
							public int compare(InstrumentInfo a, InstrumentInfo b) {
								return a.name.compareTo(b.name);
							}
						});
					}
				}
			}
		}
		
		// Create category buttons
		// Remember last selected category
		android.content.SharedPreferences prefs = getSharedPreferences("WaveSynthPlus", MODE_PRIVATE);
		String lastCategory = prefs.getString("lastInstrumentCategory", null);
		if (lastCategory == null || !categoryMap.containsKey(lastCategory)) {
			lastCategory = categoryMap.isEmpty() ? null : categoryMap.keySet().iterator().next();
		}
		final String[] selectedCategory = {lastCategory};
		
		for (final String category : categoryMap.keySet()) {
			final int instrumentCount = categoryMap.get(category).size();
			
			Button categoryBtn = new Button(this);
			android.text.SpannableString text = new android.text.SpannableString(
				category + "\n(" + instrumentCount + ")"
			);
			text.setSpan(new android.text.style.RelativeSizeSpan(0.7f), 
				category.length(), text.length(), 
				android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			text.setSpan(new android.text.style.ForegroundColorSpan(0xFFAAAAAA), 
				category.length(), text.length(), 
				android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			categoryBtn.setText(text);
			categoryBtn.setTextSize(12);
			categoryBtn.setPadding(8, 8, 8, 8);
			LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
			);
			btnParams.setMargins(0, 4, 0, 4);
			categoryBtn.setLayoutParams(btnParams);
			
			categoryBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Show warning for Noise category
					if (category.equals("Noise")) {
						MessageDialog warningDialog = new MessageDialog(MainActivity.this, 
							"⚠️ Volume Warning", 
							"Noise instruments are EXTREMELY LOUD even at low volume settings!\n\nPlease lower your device volume to the LOWEST possible level before proceeding.",
							new String[] { "OK, I Understand" });
						warningDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
							@Override
							public void onDismiss(DialogInterface d) {
								selectedCategory[0] = category;
								getSharedPreferences("WaveSynthPlus", MODE_PRIVATE).edit()
									.putString("lastInstrumentCategory", category).apply();
								updateInstrumentList(categoryMap, category, instrumentList, dialog);
								updateCategoryButtonStates(categoryContainer, category);
							}
						});
						warningDialog.show();
					} else {
						selectedCategory[0] = category;
						getSharedPreferences("WaveSynthPlus", MODE_PRIVATE).edit()
							.putString("lastInstrumentCategory", category).apply();
						updateInstrumentList(categoryMap, category, instrumentList, dialog);
						updateCategoryButtonStates(categoryContainer, category);
					}
				}
			});
			
			categoryBtn.setAlpha(category.equals(selectedCategory[0]) ? 1.0f : 0.6f);
			categoryContainer.addView(categoryBtn);
		}
		
		// Show initial category
		if (selectedCategory[0] != null) {
			updateInstrumentList(categoryMap, selectedCategory[0], instrumentList, dialog);
		}
		
		dialog.show();
	}
	
	private String formatFileSize(long bytes) {
		if (bytes < 1024) {
			return bytes + " B";
		} else if (bytes < 1024 * 1024) {
			return String.format("%.1f KB", bytes / 1024.0);
		} else {
			return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
		}
	}
	
	private void updateCategoryButtonStates(LinearLayout container, String selectedCategory) {
		for (int i = 0; i < container.getChildCount(); i++) {
			View child = container.getChildAt(i);
			if (child instanceof Button) {
				Button btn = (Button) child;
				String btnCategory = btn.getText().toString().split("\n")[0];
				btn.setAlpha(btnCategory.equals(selectedCategory) ? 1.0f : 0.6f);
			}
		}
	}
	
	private void updateInstrumentList(final java.util.Map<String, java.util.ArrayList<InstrumentInfo>> categoryMap, 
	                                  final String category, 
	                                  ListView instrumentList, 
	                                  final android.app.Dialog dialog) {
		final java.util.ArrayList<InstrumentInfo> instruments = categoryMap.get(category);
		if (instruments == null) return;
		
		// Get currently loaded instrument path
		final String currentInstrument = clientModel.getInstrumentName();
		
		// Custom adapter to show metadata
		android.widget.BaseAdapter adapter = new android.widget.BaseAdapter() {
			@Override
			public int getCount() {
				return instruments.size();
			}
			
			@Override
			public Object getItem(int position) {
				return instruments.get(position);
			}
			
			@Override
			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public View getView(int position, View convertView, android.view.ViewGroup parent) {
				if (convertView == null) {
					convertView = getLayoutInflater().inflate(R.layout.instrument_list_item, parent, false);
				}
				
				InstrumentInfo info = instruments.get(position);
				
				TextView nameView = convertView.findViewById(R.id.instrumentName);
				TextView typeView = convertView.findViewById(R.id.instrumentType);
				TextView sizeView = convertView.findViewById(R.id.instrumentSize);
				TextView authorView = convertView.findViewById(R.id.instrumentAuthor);
				View selectionIndicator = convertView.findViewById(R.id.selectionIndicator);
				
				nameView.setText(info.displayName);
				typeView.setText(info.type);
				sizeView.setText(formatFileSize(info.sizeBytes));
				
				// Display author if available
				if (authorView != null) {
					if (info.author != null && !info.author.isEmpty()) {
						authorView.setText(info.author);
						authorView.setVisibility(View.VISIBLE);
					} else {
						authorView.setVisibility(View.GONE);
					}
				}
				
				// Show selection indicator if this is the current instrument
				String fullPath = info.category + "/" + info.name;
				if (fullPath.equals(currentInstrument)) {
					selectionIndicator.setVisibility(View.VISIBLE);
				} else {
					selectionIndicator.setVisibility(View.GONE);
				}
				
				return convertView;
			}
		};
		
		instrumentList.setAdapter(adapter);
	
	instrumentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			InstrumentInfo info = instruments.get(position);
		String fullPath = info.category + "/" + info.name;
		
		// Update spinner text to show selected instrument
		soundSpinner.setSelection(0); // Reset to avoid listener trigger
		ArrayAdapter<CharSequence> spinnerAdapter = (ArrayAdapter<CharSequence>) soundSpinner.getAdapter();
		if (spinnerAdapter.getCount() > 0) {
			spinnerAdapter.clear();
			spinnerAdapter.add(fullPath);
			soundSpinner.setSelection(0);
		}
		
		// Load the sound
		loadSound(fullPath);
		
		dialog.dismiss();
	}
});
	}

	public void updateSoundSpinner() {
		ArrayAdapter<CharSequence> soundAdapter = new ArrayAdapter(this, R.layout.spinner_item);

		try {
			if (getIntent().getData() != null) {
				soundAdapter.add(getIntent().getData().toString());
			}
			
			// Scan instruments folder with categories
			try {
				String[] categories = getAssets().list("instruments");
				if (categories != null && categories.length > 0) {
					for (String category : categories) {
						String[] files = getAssets().list("instruments/" + category);
						if (files != null) {
							for (String filename : files) {
								int synthpos = filename.indexOf(".easysynth");
								int wavesynthpos = filename.indexOf(".wavesynth");
								if (synthpos >= 0) {
									String name = filename.substring(0, synthpos);
									soundAdapter.add(category + "/" + name);
								} else if (wavesynthpos >= 0) {
									String name = filename.substring(0, wavesynthpos);
									soundAdapter.add(category + "/" + name);
								}
							}
						}
					}
				}
			} catch (IOException e) {
				// No instruments folder, fall back to old method
			}
			
			// Also scan root assets for backwards compatibility
			String[] assets = getAssets().list("");
			for (int i = 0; i < assets.length; i++) {
				String filename = assets[i];
				int synthpos = filename.indexOf(".easysynth");
				int wavesynthpos = filename.indexOf(".wavesynth");
				if (synthpos >= 0) {
					String name = filename.substring(0, synthpos);
					soundAdapter.add(name);
				} else if (wavesynthpos >= 0) {
					String name = filename.substring(0, wavesynthpos);
					soundAdapter.add(name);
				}
			}
			
			File filesDir = getFilesDir();
			
			// Scan user files in subdirectories (categories)
			File[] categoryDirs = filesDir.listFiles();
			if (categoryDirs != null) {
				for (File catDir : categoryDirs) {
					if (catDir.isDirectory()) {
						String category = catDir.getName();
						File[] catFiles = catDir.listFiles();
						if (catFiles != null) {
							for (File file : catFiles) {
								String filename = file.getName();
								if (filename.endsWith(".wavesynth") || filename.endsWith(".easysynth")) {
									int dotPos = filename.lastIndexOf('.');
									if (dotPos > 0) {
										String name = filename.substring(0, dotPos);
										soundAdapter.add(category + "/" + name);
									}
								}
							}
						}
					}
				}
			}
			
			// Also scan root filesDir for legacy files
			File[] files = filesDir.listFiles();
			// Arrays.sort(files, new Comparator<File>() {
			//
			// };
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					String filename = files[i].getName();
					boolean found = false;
					for (int j = 0; j < assets.length; j++) {
						if (filename.equals(assets[j])) {
							found = true;
						}
					}
					if (!found) {
						int synthpos = filename.indexOf(".easysynth");
						if (synthpos >= 0) {
							String name = filename.substring(0, synthpos);
							soundAdapter.add(name);
						}
					}
				}
			}

			filesDir = getExternalFilesDir(null);
			files = filesDir.listFiles();
			// Arrays.sort(files, new Comparator<File>() {
			//
			// };
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					String filename = files[i].getName();
					boolean found = false;
					for (int j = 0; j < assets.length; j++) {
						if (filename.equals(assets[j])) {
							found = true;
						}
					}
					if (!found) {
						int synthpos = filename.indexOf(".wavesynth");
						if (synthpos >= 0) {
							String name = filename.substring(0, synthpos);
							soundAdapter.add(name);
						}
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		soundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		soundSpinner.setAdapter(soundAdapter);
	}

	public void saveSound() {
		String initiallSoundName = (String) soundSpinner.getSelectedItem();
		if (initiallSoundName.startsWith("file://")) {
			initiallSoundName = initiallSoundName.substring(initiallSoundName.lastIndexOf("/") + 1);
			initiallSoundName = initiallSoundName.substring(0, initiallSoundName.length() - ".easysynth".length());
		}
		// Remove category prefix if present
		if (initiallSoundName.contains("/")) {
			initiallSoundName = initiallSoundName.substring(initiallSoundName.lastIndexOf("/") + 1);
		}
		final String initialSoundName = initiallSoundName;
		
		// Get available categories
		final java.util.ArrayList<String> categories = new java.util.ArrayList<>();
		try {
			String[] categoryArray = getAssets().list("instruments");
			if (categoryArray != null) {
				java.util.Arrays.sort(categoryArray);
				for (String cat : categoryArray) {
					categories.add(cat);
				}
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		
		// Create dialog with category spinner and name input
		final android.app.Dialog saveDialog = new android.app.Dialog(this, R.style.Theme_Dialog);
		saveDialog.setContentView(R.layout.save_dialog);
		saveDialog.setTitle("Save Sound");
		
		// Make dialog wider like instrument dialog
		if (saveDialog.getWindow() != null) {
			android.view.WindowManager.LayoutParams lp = new android.view.WindowManager.LayoutParams();
			lp.copyFrom(saveDialog.getWindow().getAttributes());
			lp.width = (int)(getResources().getDisplayMetrics().widthPixels * 0.6);
			lp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
			saveDialog.getWindow().setAttributes(lp);
		}
		
		final EditText nameInput = saveDialog.findViewById(R.id.soundNameInput);
		final EditText authorInput = saveDialog.findViewById(R.id.authorInput);
		final Spinner categorySpinner = saveDialog.findViewById(R.id.categorySpinner);
		Button saveSoundButton = saveDialog.findViewById(R.id.saveButton);
		Button cancelButton = saveDialog.findViewById(R.id.cancelButton);
		
		nameInput.setText(initialSoundName);
		
		// Pre-fill author if sound already has one
		if (sound.author != null && !sound.author.isEmpty()) {
			authorInput.setText(sound.author);
		}
		
		// Setup category spinner
		ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, categories);
		categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorySpinner.setAdapter(categoryAdapter);
		
		// Select current category if available
		String currentInstrument = clientModel.getInstrumentName();
		if (currentInstrument != null && currentInstrument.contains("/")) {
			String currentCategory = currentInstrument.substring(0, currentInstrument.indexOf("/"));
			for (int i = 0; i < categories.size(); i++) {
				if (categories.get(i).equals(currentCategory)) {
					categorySpinner.setSelection(i);
					break;
				}
			}
		}
		
		saveSoundButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String soundName = nameInput.getText().toString().trim();
				String author = authorInput.getText().toString().trim();
				String category = (String) categorySpinner.getSelectedItem();
				
				if (soundName != null && !soundName.isEmpty() && category != null) {
					Sound sound = instrument.generateSound();
					if (author != null && !author.isEmpty()) {
						sound.author = author;
					}
					String fullPath = category + "/" + soundName;
					
					// Create category directory if it doesn't exist
					try {
						java.io.File categoryDir = new java.io.File(getFilesDir(), category);
						if (!categoryDir.exists()) {
							categoryDir.mkdirs();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					java.io.File savedFile = clientModel.saveObject(sound, fullPath + ".wavesynth", true);
					if (savedFile != null) {
						dirty = false;
						
						// Update the spinner and select the newly saved sound
						updateSoundSpinner();
						int newSoundPosition = -1;
						for (int i = 0; i < soundSpinner.getCount(); i++) {
							if (fullPath.equals(soundSpinner.getItemAtPosition(i))) {
								newSoundPosition = i;
								break;
							}
						}
						if (newSoundPosition >= 0) {
							soundSpinner.setSelection(newSoundPosition);
						}
						
						Toast.makeText(MainActivity.this, "Sound saved to " + fullPath, Toast.LENGTH_SHORT).show();
						saveDialog.dismiss();
					} else {
						Toast.makeText(MainActivity.this, "Failed to save sound", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(MainActivity.this, "Please enter a sound name", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveDialog.dismiss();
			}
		});
		
		saveDialog.show();
	}

	public void sendSound() {
		final String soundName = (String) soundSpinner.getSelectedItem();
		try {
			Sound sound = instrument.generateSound();

//			File file = clientModel.exportObject(sound, soundName + ".easysynth");
//			System.out.println("Exported sound as " + soundName + ".easysynth");
			File file = clientModel.saveObject(sound, soundName + ".wavesynth", true);
			System.out.println("Sending sound in file "+file);
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("application/vnd.gallantrealm.wavesynth");
//			Uri uri = Uri.fromFile(file);
			Uri uri = FileProvider.getUriForFile(this, "com.gallantrealm.wavesynth.fileprovider", file);
			intent.putExtra(Intent.EXTRA_STREAM, uri);
			intent.putExtra(Intent.EXTRA_SUBJECT, soundName);
			if (clientModel.isAmazon()) {
				intent.putExtra(Intent.EXTRA_TEXT, "Sharing an WaveSynth instrument with you.  You can play it by installing EasySynth from the Amazon AppStore!");
			} else {
				intent.putExtra(Intent.EXTRA_TEXT, "Sharing an WaveSynth instrument with you.  You can play it by installing EasySynth from Google Play at  http://play.google.com/store/apps/details?id=com.gallantrealm.easysynth");
			}
			clientModel.getContext().startActivity(intent);
		} catch (Exception e) { // might fail
			// ignore
		}
	}

	public void loadSound(String soundName) {
		if (soundName.startsWith("file://")) { // playing a sent sound
			sound = (Sound) clientModel.loadObject(soundName, true);
			applySound(sound);
			System.out.println("Loaded external file sound " + soundName);
		} else {
			// Try loading with category path (e.g., "instruments/Keyboards/piano")
			sound = (Sound) clientModel.loadObject("instruments/" + soundName + ".wavesynth", true);
			if (sound != null) {
				applySound(sound);
				System.out.println("Loaded categorized sound " + soundName + ".wavesynth");
			} else {
				sound = (Sound) clientModel.loadObject("instruments/" + soundName + ".easysynth", true);
				if (sound != null) {
					applySound(sound);
					System.out.println("Loaded categorized sound " + soundName + ".easysynth");
				} else {
					// Try without category prefix (backwards compatibility)
					sound = (Sound) clientModel.loadObject(soundName + ".wavesynth", true);
					if (sound != null) {
						applySound(sound);
						System.out.println("Loaded external file sound " + soundName + ".wavesynth");
					} else { // try internal (easysynth)
						sound = (Sound) clientModel.loadObject(soundName + ".easysynth", true);
						if (sound != null) {
							applySound(sound);
							System.out.println("Loaded internal file sound " + soundName + ".easysynth");
						} else {
							System.out.println("Sound couldn't be loaded!!!");
						}
					}
				}
			}
			clientModel.setInstrumentName(soundName);
			clientModel.savePreferences(this);
		}
		soundLoadTime = System.currentTimeMillis();
	}

	public void deleteSound() {
		final String soundName = (String) soundSpinner.getSelectedItem();
		final MessageDialog promptForDelete = new MessageDialog(this, "Delete", "Delete sound?", new String[] { "OK", "Cancel" });
		promptForDelete.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				if (promptForDelete.getButtonPressed() == 0) {
					clientModel.deleteObject(soundName + ".easysynth", true);
					System.out.println("Deleted sound " + soundName + ".easysynth");
					updateSoundSpinner();
				}
			}
		});
		promptForDelete.show();
	}

	public boolean unsavedRecording;

	public void startRecording() {
		if (unsavedRecording) {
			final MessageDialog message = new MessageDialog(MainActivity.this, null, "Erase unsaved recording?", new String[] { "OK", "Cancel" });
			message.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					if (message.getButtonPressed() == 0) {
						synth.startRecording();
						if (updateThread == null) {
							stopUpdateThread = false;
							updateThread = new UpdateThread();
							updateThread.start();
						}
						unsavedRecording = true;
					}
				}
			});
			message.show();
		} else {
			synth.startRecording();
			if (updateThread == null) {
				stopUpdateThread = false;
				updateThread = new UpdateThread();
				updateThread.start();
			}
			unsavedRecording = true;
		}
	}

	public void stopRecording() {
		synth.stopRecording();
		if (updateThread != null) {
			updateThread.safeStop();
			updateThread = null;
		}
	}

	public void playbackRecording() {
		synth.playbackRecording();
		if (updateThread == null) {
			stopUpdateThread = false;
			updateThread = new UpdateThread();
			updateThread.start();
		}
	}

	final private int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 123;

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
		case REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE:
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				saveRecording();
			} else {
				Toast.makeText(MainActivity.this, "Cannot save.  Permission denied.", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}

	public String lastRecordName = "jam";

	@SuppressLint("NewApi")
	public void saveRecording() {
//		if (!clientModel.isFullVersion() && !clientModel.isGoggleDogPass()) {
//			MessageDialog messageDialog = new MessageDialog(this, "Save Disabled", "Buy full version to enable save.", null);
//			messageDialog.show();
//			return;
//		}
		if (Build.VERSION.SDK_INT < 29 && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
			return;
		}
		final InputDialog inputDialog = new InputDialog(this, "Save Recording", "Recording name:", lastRecordName, new String[] { "Save", "Cancel" });
		inputDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				if (inputDialog.getButtonPressed() == 0) {
					final String recordname = inputDialog.getValue();
					lastRecordName = recordname;

					if (Build.VERSION.SDK_INT < 29) {
						File musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
						if (!musicDir.exists()) {
							System.out.println("making Music directory");
							boolean made = musicDir.mkdir();
							if (!made) {
								System.out.println("Music directory could not be made!!");
							}
						}
						final File easySynthDir = new File(musicDir, "WaveSynth");
						if (!easySynthDir.exists()) {
							System.out.println("making Music/WaveSynth");
							boolean made = easySynthDir.mkdir();
							if (!made) {
								System.out.println("Music/WaveSynth could not be made!!");
							}
						}

						final String filename = easySynthDir.getPath() + "/" + recordname + ".wav";
						System.out.println("Saving recording to " + filename);

						File file = new File(filename);
						if (file.exists()) {
							final InputDialog confirmDialog = new InputDialog(MainActivity.this, "File Exists", "File already exists with name:", lastRecordName, new String[]{"Overwrite", "Cancel"});
							confirmDialog.setOnDismissListener(new OnDismissListener() {

								@Override
								public void onDismiss(DialogInterface dialog) {
									if (confirmDialog.getButtonPressed() == 0) {
										finishSavingRecording(file);
									}
								}
							});
							confirmDialog.show();
						} else {
							finishSavingRecording(file);
						}
					} else { // sdk >= 29
						ContentResolver contentResolver = MainActivity.this.getContentResolver();
						ContentValues contentValues = new ContentValues();
						contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, recordname + ".wav");
						contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "audio/wav");
						contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "Music/WaveSynth");
						Uri uri = contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues);
						try {
							OutputStream outputStream = contentResolver.openOutputStream(uri);
							synth.saveRecording(new BufferedOutputStream(outputStream));
							System.out.println("Recording saved.");
							unsavedRecording = false;
							Toast toast = Toast.makeText(MainActivity.this, "Saved to Music/WaveSynth/" + recordname + ".wav", Toast.LENGTH_LONG);
							toast.show();
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
		});
		inputDialog.show();
	}

	private void finishSavingRecording(File file) {
		try {
			synth.saveRecording(new BufferedOutputStream(new FileOutputStream(file)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));

		System.out.println("Recording saved.");
		unsavedRecording = false;

		Toast toast = Toast.makeText(MainActivity.this, "Saved to " + file, Toast.LENGTH_LONG);
		toast.show();
	}

	boolean applyingASound = false;

	private void applySound(Sound sound) {
		if (sound == null) {
			System.out.println("Sound is null!");
			return;
		}
		applyingASound = true;
		instrument.applySound(sound);
		updateControls();
		applyingASound = false;
	}

	private void updateControls() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
//				if (!clientModel.isFullVersion() && !clientModel.isGoggleDogPass() && Locale.getDefault().getLanguage().startsWith("en")) {
//					buyButton.setVisibility(View.VISIBLE);
//				} else {
					buyButton.setVisibility(View.GONE);
//				}
				applyingASound = true;
				// set the controls
				if (instrument.mode == 0) {
					monoRadio.setChecked(true);
				} else if (instrument.mode == 1) {
					polyRadio.setChecked(true);
				} else if (instrument.mode == 2) {
					chorusRadio.setChecked(true);
				}
				chorusWidth.setProgress((int) Math.sqrt(instrument.chorusWidth * 10000));
				portamentoAmount.setProgress((int) (instrument.portamentoAmount * 101));
				velocitySpinner.setSelection(instrument.velocityType);
				expressionSpinner.setSelection(instrument.expressionType);
				if (instrument.octave == 1) {
					octave2Radio.setChecked(true);
				} else if (instrument.octave == 2) {
					octave3Radio.setChecked(true);
				} else if (instrument.octave == 3) {
					octave4Radio.setChecked(true);
				} else if (instrument.octave == 4) {
					octave5Radio.setChecked(true);
				} else if (instrument.octave == 5) {
					octave6Radio.setChecked(true);
				}
				keySpinner.setSelection(instrument.key);
				holdButton.setChecked(instrument.hold);
				harmonic1.setProgress((int) (Math.sqrt(instrument.harmonics[0] * 20000)));
				harmonic2.setProgress((int) (Math.sqrt(instrument.harmonics[1] * 20000)));
				harmonic3.setProgress((int) (Math.sqrt(instrument.harmonics[2] * 20000)));
				harmonic4.setProgress((int) (Math.sqrt(instrument.harmonics[3] * 20000)));
				harmonic5.setProgress((int) (Math.sqrt(instrument.harmonics[4] * 20000)));
				harmonic6.setProgress((int) (Math.sqrt(instrument.harmonics[5] * 20000)));
				harmonic7.setProgress((int) (Math.sqrt(instrument.harmonics[6] * 20000)));
				harmonic8.setProgress((int) (Math.sqrt(instrument.harmonics[7] * 20000)));
				harmonic9.setProgress((int) (Math.sqrt(instrument.harmonics[8] * 20000)));
				harmonic10.setProgress((int) (Math.sqrt(instrument.harmonics[9] * 20000)));
				harmonic11.setProgress((int) (Math.sqrt(instrument.harmonics[10] * 20000)));
				harmonic12.setProgress((int) (Math.sqrt(instrument.harmonics[11] * 20000)));
				if (instrument.harmonics.length > 12) {
					harmonic13.setProgress((int) (Math.sqrt(instrument.harmonics[12] * 20000)));
					harmonic14.setProgress((int) (Math.sqrt(instrument.harmonics[13] * 20000)));
					harmonic15.setProgress((int) (Math.sqrt(instrument.harmonics[14] * 20000)));
					harmonic16.setProgress((int) (Math.sqrt(instrument.harmonics[15] * 20000)));
				} else {
					harmonic13.setProgress(0);
					harmonic14.setProgress(0);
					harmonic15.setProgress(0);
					harmonic16.setProgress(0);
				}
				if (instrument.harmonics.length > 16) {
					harmonic17.setProgress((int) (Math.sqrt(instrument.harmonics[16] * 20000)));
					harmonic18.setProgress((int) (Math.sqrt(instrument.harmonics[17] * 20000)));
					harmonic19.setProgress((int) (Math.sqrt(instrument.harmonics[18] * 20000)));
					harmonic20.setProgress((int) (Math.sqrt(instrument.harmonics[19] * 20000)));
					harmonic21.setProgress((int) (Math.sqrt(instrument.harmonics[20] * 20000)));
					harmonic22.setProgress((int) (Math.sqrt(instrument.harmonics[21] * 20000)));
					harmonic23.setProgress((int) (Math.sqrt(instrument.harmonics[22] * 20000)));
					harmonic24.setProgress((int) (Math.sqrt(instrument.harmonics[23] * 20000)));
					harmonic25.setProgress((int) (Math.sqrt(instrument.harmonics[24] * 20000)));
					harmonic26.setProgress((int) (Math.sqrt(instrument.harmonics[25] * 20000)));
					harmonic27.setProgress((int) (Math.sqrt(instrument.harmonics[26] * 20000)));
					harmonic28.setProgress((int) (Math.sqrt(instrument.harmonics[27] * 20000)));
					harmonic29.setProgress((int) (Math.sqrt(instrument.harmonics[28] * 20000)));
					harmonic30.setProgress((int) (Math.sqrt(instrument.harmonics[29] * 20000)));
					harmonic31.setProgress((int) (Math.sqrt(instrument.harmonics[30] * 20000)));
					harmonic32.setProgress((int) (Math.sqrt(instrument.harmonics[31] * 20000)));
				} else {
					harmonic17.setProgress(0);
					harmonic18.setProgress(0);
					harmonic19.setProgress(0);
					harmonic20.setProgress(0);
					harmonic21.setProgress(0);
					harmonic22.setProgress(0);
					harmonic23.setProgress(0);
					harmonic24.setProgress(0);
					harmonic25.setProgress(0);
					harmonic26.setProgress(0);
					harmonic27.setProgress(0);
					harmonic28.setProgress(0);
					harmonic29.setProgress(0);
					harmonic30.setProgress(0);
					harmonic31.setProgress(0);
					harmonic32.setProgress(0);
				}
				if (instrument.filterType == WaveSynth.FILTER_LOWPASS) {
					filterTypeSpinner.setSelection(0);
				} else if (instrument.filterType == WaveSynth.FILTER_BANDPASS) {
					filterTypeSpinner.setSelection(1);
				} else if (instrument.filterType == WaveSynth.FILTER_FADE) {
					filterTypeSpinner.setSelection(3);
				} else if (instrument.filterType == WaveSynth.FILTER_HIGHPASS) {
					filterTypeSpinner.setSelection(2);
				} else if (instrument.filterType == WaveSynth.FILTER_COMB1) {
					filterTypeSpinner.setSelection(4);
				} else if (instrument.filterType == WaveSynth.FILTER_COMB2) {
					filterTypeSpinner.setSelection(5);
				} else if (instrument.filterType == WaveSynth.FILTER_COMB3) {
					filterTypeSpinner.setSelection(6);
				} else if (instrument.filterType == WaveSynth.FILTER_COMB4) {
					filterTypeSpinner.setSelection(7);
				} else if (instrument.filterType == WaveSynth.FILTER_FORMANT1) {
					filterTypeSpinner.setSelection(8);
				} else if (instrument.filterType == WaveSynth.FILTER_FORMANT2) {
					filterTypeSpinner.setSelection(9);
				} else if (instrument.filterType == WaveSynth.FILTER_FORMANT3) {
					filterTypeSpinner.setSelection(10);
				} else if (instrument.filterType == WaveSynth.FILTER_FORMANT4) {
					filterTypeSpinner.setSelection(11);
				}
				filterResonance.setProgress((int) (instrument.filterResonance * 100));
				filterLow.setProgress((int) (instrument.filterLow * 100));
				filterHigh.setProgress((int) (instrument.filterHigh * 100));
				filterEnvAttack.setProgress((int) (Math.sqrt((instrument.filterEnvAttack) * 50)));
				filterEnvDecay.setProgress((int) (Math.sqrt((instrument.filterEnvDecay) * 50)));
				filterEnvSustain.setProgress((int) (instrument.filterEnvSustain * 100));
				filterEnvRelease.setProgress((int) (Math.sqrt((instrument.filterEnvRelease) * 50)));
				ampVolume.setProgress((int) (instrument.ampVolume * 100));
				ampAttack.setProgress((int) (Math.sqrt((instrument.ampAttack) * 50)));
				ampDecay.setProgress((int) (Math.sqrt((instrument.ampDecay) * 50)));
				ampSustain.setProgress((int) (instrument.ampSustain * 100));
				ampRelease.setProgress((int) (Math.sqrt((instrument.ampRelease) * 50)));
				ampOverdrive.setProgress(instrument.ampOverdrive);
				ampOverdrivePerVoiceCheckBox.setChecked(instrument.ampOverdrivePerVoice);
				vibratoDestinationSpinner.setSelection(instrument.vibratoDestination);
				vibratoTypeSpinner.setSelection(instrument.vibratoType);
				vibratoAmount.setProgress((int) (Math.sqrt(instrument.vibratoAmount * 25000)));
				vibratoRate.setProgress((int) (Math.pow((instrument.vibratoRate - 1) * 1000, 1.0 / 3))); // synth.vibratoRate
																									// =
																									// progress
																									// *
																									// progress
																									// *
																									// progress
																									// /
																									// 1000.0f
																									// +
																									// 1;
				vibratoSyncCheckBox.setChecked(instrument.vibratoSync);
				vibratoAttack.setProgress((int) (Math.sqrt((instrument.vibratoAttack) * 50)));
				vibratoDecay.setProgress((int) (Math.sqrt((instrument.vibratoDecay) * 50)));
				vibrato2DestinationSpinner.setSelection(instrument.vibrato2Destination);
				vibrato2TypeSpinner.setSelection(instrument.vibrato2Type);
				vibrato2Amount.setProgress((int) (Math.sqrt(instrument.vibrato2Amount * 25000)));
				vibrato2Rate.setProgress((int) (Math.pow((instrument.vibrato2Rate - 1) * 1000, 1.0 / 3))); // synth.vibrato2Rate
																										// =
																										// progress
																										// *
																										// progress
																										// *
																										// progress
																										// /
																										// 1000.0f
																										// +
																										// 1;
				vibrato2SyncCheckBox.setChecked(instrument.vibrato2Sync);
				vibrato2Attack.setProgress((int) (Math.sqrt((instrument.vibrato2Attack) * 50)));
				vibrato2Decay.setProgress((int) (Math.sqrt((instrument.vibrato2Decay) * 50)));
				echoAmount.setProgress((int) (instrument.echoAmount * 100));
				echoDelay.setProgress((int) (Math.sqrt((instrument.echoDelay * 10201))));
				echoFeedback.setProgress((int) (instrument.echoFeedback * 101));
				flangeAmount.setProgress((int) (instrument.flangeAmount * 100));
				flangeRate.setProgress((int) (instrument.flangeRate * 100));
				distortionAmount.setProgress((int) (instrument.distortionAmount * 100));
				bitCrush.setProgress((int) (instrument.bitCrushAmount * 100));
				whiteNoiseCheckBox.setChecked(instrument.whiteNoiseAmount > 0.5f);
				pinkNoiseAmount.setProgress((int) (instrument.pinkNoiseAmount * 100));
				brownNoiseAmount.setProgress((int) (instrument.brownNoiseAmount * 100));
				staticNoiseAmount.setProgress((int) (instrument.staticNoiseAmount * 100));
				reverbAmount.setProgress((int) (instrument.reverbAmount * 100));
				limiterAmount.setProgress((int) (instrument.limiterAmount * 100));
				compressorAmount.setProgress((int) (instrument.compressorAmount * 100));
				sequence1.setProgress((instrument.sequence[0]));
				sequence2.setProgress((instrument.sequence[1]));
				sequence3.setProgress((instrument.sequence[2]));
				sequence4.setProgress((instrument.sequence[3]));
				sequence5.setProgress((instrument.sequence[4]));
				sequence6.setProgress((instrument.sequence[5]));
				sequence7.setProgress((instrument.sequence[6]));
				sequence8.setProgress((instrument.sequence[7]));
				if (instrument.sequence.length > 8) {
					sequence1.setProgress((instrument.sequence[0]));
					sequence2.setProgress((instrument.sequence[1]));
					sequence3.setProgress((instrument.sequence[2]));
					sequence4.setProgress((instrument.sequence[3]));
					sequence5.setProgress((instrument.sequence[4]));
					sequence6.setProgress((instrument.sequence[5]));
					sequence7.setProgress((instrument.sequence[6]));
					sequence8.setProgress((instrument.sequence[7]));
					sequence9.setProgress((instrument.sequence[8]));
					sequence10.setProgress((instrument.sequence[9]));
					sequence11.setProgress((instrument.sequence[10]));
					sequence12.setProgress((instrument.sequence[11]));
					sequence13.setProgress((instrument.sequence[12]));
					sequence14.setProgress((instrument.sequence[13]));
					sequence15.setProgress((instrument.sequence[14]));
					sequence16.setProgress((instrument.sequence[15]));
				} else {
					sequence9.setProgress(0);
					sequence10.setProgress(0);
					sequence11.setProgress(0);
					sequence12.setProgress(0);
					sequence13.setProgress(0);
					sequence14.setProgress(0);
					sequence15.setProgress(0);
					sequence16.setProgress(0);
				}
				// sequenceRate.setProgress((int) synth.sequenceRate);
				sequenceRateTextView.setText(String.valueOf((int) instrument.sequenceRate));
				sequenceLoopCheckBox.setChecked(instrument.sequenceLoop);

				applyingASound = false;
			}
		});
	}

	@Override
	protected void onStart() {
		System.out.println(">MainActivity.start");
		super.onStart();
		if (getIntent().getData() != null) {
			System.out.println("Invocation params: " + getIntent().getData());
			loadSound(getIntent().getData().toString());
		}
		wakelock.acquire();
		soundLoadTime = System.currentTimeMillis();
		try {
			synth.start();
			synth.setInstrument(instrument);
			midi.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.out.println("<MainActivity.start");
		}
	}

	@Override
	protected void onStop() {
		System.out.println(">MainActivity.stop");
		super.onStop();
		if (updateThread != null) {
			stopRecording();
		}
		wakelock.release();
		midi.stop();
		synth.stop();
		System.out.println("<MainActivity.stop");
	}

	@Override
	protected void onResume() {
		super.onResume();
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		boolean isScreenOn = pm.isScreenOn();
		if (isScreenOn) {
			// synth.start();
		}

		// hide system navigation
		final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
		getWindow().getDecorView().setSystemUiVisibility(flags);

		soundLoadTime = System.currentTimeMillis();
		
		// Apply extended range setting
		applyExtendedRange();
		
		// Start CPU monitoring
		startCpuMonitoring();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			// hide system navigation
			getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
	}

	@Override
	protected void onPause() {
		long soundPlayTime = System.currentTimeMillis() - soundLoadTime;
		System.out.println("recordMetric: " + clientModel.getInstrumentName() + ": " + (soundPlayTime / 60000.0));
		super.onPause();
		// synth.stop();
		
		// Stop CPU monitoring
		stopCpuMonitoring();
	}

	int[] lastNote = new int[20]; // for finger
	int lastNote1;
	int lastNote2;
	int lastNote3;
	int lastNote4;
	int[] lastVoice = new int[20];

	private static final int PRESS = 1;
	private static final int RELEASE = 2;
	private static final int SLIDE = 0;

	private float lastTouchDownX;
	private float lastTouchDownY;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int index = event.getActionIndex();
		int pointerCount = event.getPointerCount();

		float x = event.getX(index);
		float y = event.getY(index);
		if (keyboardLocation == null) {
			keyboardLocation = new int[2];
			keyboard.getLocationOnScreen(keyboardLocation);
		}
		x += keyboardLocation[0];
		y += keyboardLocation[1];
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
			if (v == sequenceRateUpButton) {
				instrument.sequenceRate = Math.min(1000, instrument.sequenceRate + 1);
				instrument.updateParams();
				updateControls();
				startAutoRepeat(v);
			} else if (v == sequenceRateDownButton) {
				instrument.sequenceRate = Math.max(0, instrument.sequenceRate - 1);
				instrument.updateParams();
				updateControls();
				startAutoRepeat(v);
			} else {
				doKey(event.getPointerId(index) + 1, x, y, PRESS, pointerCount);
				lastTouchDownX = x;
				lastTouchDownY = y;
			}
		} else if (event.getActionMasked() == MotionEvent.ACTION_UP || event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
			if (v == sequenceRateUpButton) {
				stopAutoRepeat();
			} else if (v == sequenceRateDownButton) {
				stopAutoRepeat();
			} else {
				doKey(event.getPointerId(index) + 1, x, y, RELEASE, pointerCount);
			}
		} else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
			if (v == sequenceRateUpButton) {
			} else if (v == sequenceRateDownButton) {
			} else {
				for (int i = 0; i < pointerCount; i++) {
					int historySize = event.getHistorySize();
					for (int h = 0; h < historySize; h++) {
						doKey(event.getPointerId(i) + 1, event.getHistoricalX(i, h) + keyboardLocation[0], Math.max(0, event.getHistoricalY(i, h) + keyboardLocation[1]), SLIDE, pointerCount);
					}
					doKey(event.getPointerId(i) + 1, event.getX(i) + keyboardLocation[0], Math.max(0, event.getY(i) + keyboardLocation[1]), SLIDE, pointerCount);
				}
			}
		}
		return true;
	}

	class AutoRepeatThread extends Thread {
		View v;
		boolean stoppit;
		int sleepTime = 200;

		public AutoRepeatThread(View v) {
			super("AutoRepeatThread");
			this.v = v;
		}

		@Override
		public void run() {
			try {
				while (!stoppit) {
					Thread.sleep(sleepTime);
					sleepTime = Math.max(10, (int) (sleepTime * 0.95f));
					if (!stoppit) {
						if (v == sequenceRateUpButton) {
							instrument.sequenceRate = Math.min(1000, instrument.sequenceRate + 1);
						} else if (v == sequenceRateDownButton) {
							instrument.sequenceRate = Math.max(0, instrument.sequenceRate - 1);
						}
						instrument.updateParams();
						MainActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								updateControls();
							}
						});
					}
				}
			} catch (InterruptedException e) {
			}
		}

		public void safeStop() {
			stoppit = true;
		}
	}

	private AutoRepeatThread autoRepeat;

	private void startAutoRepeat(View v) {
		if (autoRepeat != null) {
			autoRepeat.safeStop();
			autoRepeat = null;
		}
		autoRepeat = new AutoRepeatThread(v);
		autoRepeat.start();
	}

	private void stopAutoRepeat() {
		if (autoRepeat != null) {
			autoRepeat.safeStop();
			autoRepeat = null;
		}
	}

	private float initialX; // for pitch bend
	private float initialY; // for expression

	private final boolean[] keyHeld = new boolean[32];

	/** Show which keys are held */
	@SuppressLint("NewApi")
	private void showHolds() {
		for (int i = 0; i < 32; i++) {
			if (Build.VERSION.SDK_INT >= 11) {
				getKeyForNote(i).setActivated(keyHeld[i]);
			} else {
				getKeyForNote(i).setPressed(keyHeld[i]);
			}
		}
	}

	/** Turn off all keys */
	@SuppressLint("NewApi")
	private void clearHolds() {
		for (int i = 0; i < 32; i++) {
			keyHeld[i] = false;
		}
		showHolds();
	}

	@SuppressLint("NewApi")
	private void doKey(int finger, float x, float y, int type, int fingers) {

		if (!isPointInsideView(x, y, keyboard)) {
			int[] location = new int[2];
			keyboard.getLocationInWindow(location);
			y = location[1] + 4;
		}

		// test black keys first, then white
		int note = -1;
		while (note == -1) {
			if (isPointInsideView(x, y, key2)) {
				note = 1;
			} else if (isPointInsideView(x, y, key4)) {
				note = 3;
			} else if (isPointInsideView(x, y, key7)) {
				note = 6;
			} else if (isPointInsideView(x, y, key9)) {
				note = 8;
			} else if (isPointInsideView(x, y, key11)) {
				note = 10;
			} else if (isPointInsideView(x, y, key14)) {
				note = 13;
			} else if (isPointInsideView(x, y, key16)) {
				note = 15;
			} else if (isPointInsideView(x, y, key19)) {
				note = 18;
			} else if (isPointInsideView(x, y, key21)) {
				note = 20;
			} else if (isPointInsideView(x, y, key23)) {
				note = 22;
			} else if (x <= 0 || isPointInsideView(x, y, key1)) {
				note = 0;
			} else if (isPointInsideView(x, y, key3)) {
				note = 2;
			} else if (isPointInsideView(x, y, key5)) {
				note = 4;
			} else if (isPointInsideView(x, y, key6)) {
				note = 5;
			} else if (isPointInsideView(x, y, key8)) {
				note = 7;
			} else if (isPointInsideView(x, y, key10)) {
				note = 9;
			} else if (isPointInsideView(x, y, key12)) {
				note = 11;
			} else if (isPointInsideView(x, y, key13)) {
				note = 12;
			} else if (isPointInsideView(x, y, key15)) {
				note = 14;
			} else if (isPointInsideView(x, y, key17)) {
				note = 16;
			} else if (isPointInsideView(x, y, key18)) {
				note = 17;
			} else if (isPointInsideView(x, y, key20)) {
				note = 19;
			} else if (isPointInsideView(x, y, key21)) {
				note = 20;
			} else if (isPointInsideView(x, y, key22)) {
				note = 21;
			} else if (isPointInsideView(x, y, key24)) {
				note = 23;
			} else if (isPointInsideView(x, y, key25)) {
				note = 24;
			} else if (isPointInsideView(x, y, key26)) {
				note = 25;
			} else if (isPointInsideView(x, y, key27)) {
				note = 26;
			} else if (isPointInsideView(x, y, key28)) {
				note = 27;
			} else if (isPointInsideView(x, y, key29)) {
				note = 28;
			} else if (isPointInsideView(x, y, key30)) {
				note = 29;
			} else if (isPointInsideView(x, y, key31)) {
				note = 30;
			} else if (isPointInsideView(x, y, key32)) {
				note = 31;
			} else {
				// couldn't find a key.  Leave to avoid infinite loop
				return;
			}
		}

		int[] coords = new int[2];
		keyboard.getLocationOnScreen(coords);
		float velocity = Math.min(1.0f, 4.0f - 4.0f * (y - coords[1]) / keyboard.getHeight());

		int voice = 1;

		if (instrument.mode == WaveSynth.MODE_MONOPHONIC) {
			if (type == PRESS) {
				keyPress(1, note, velocity);
				initialX = x;
				initialY = y;
			} else if (type == RELEASE) {
				if (fingers == 1) {
					keyRelease(1, lastNote[finger]);
				} else {
					getKeyForNote(note).setPressed(false);
				}
			} else if (type == SLIDE) {
				if (note != lastNote[finger]) {
					instrument.keyRelease(1);
					getKeyForNote(lastNote[finger]).setPressed(false);
					instrument.keyPress(1, note, velocity, true, false);
					getKeyForNote(note).setPressed(true);
				} else {
					instrument.expression(FastMath.max(0, initialY - y) / keyboard.getHeight());
				}
			}
		} else if (instrument.mode == WaveSynth.MODE_CHORUS) {
			if (type == PRESS) {
				keyPress(1, note, velocity);
				initialX = x;
				initialY = y;
			} else if (type == RELEASE) {
				if (fingers == 1) {
					keyRelease(1, lastNote[finger]);
				} else {
					getKeyForNote(note).setPressed(false);
				}
			} else if (type == SLIDE) {
				if (note != lastNote[finger]) {
					instrument.keyRelease(1);
					instrument.keyRelease(2);
					instrument.keyRelease(3);
					instrument.keyRelease(4);
					getKeyForNote(lastNote[finger]).dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
					instrument.keyPress(1, note, velocity, true, false);
					getKeyForNote(note).dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
				} else {
					instrument.expression(FastMath.max(0, initialY - y) / keyboard.getHeight());
				}
			}
		} else { // polyphonic
			voice = instrument.getVoice(note);
			if (type == PRESS) {
				keyPress(voice, note, velocity);
			} else if (type == RELEASE) {
				keyRelease(voice, note);
			} else if (type == SLIDE) {
				if (!holdButton.isChecked()) {
					if (lastVoice[finger] != voice) {
						keyRelease(lastVoice[finger], lastNote[finger]);
						getKeyForNote(lastNote[finger]).dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
						instrument.keyPress(voice, note, velocity, true, false);
						getKeyForNote(note).dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
					}
				}
			}
		}
		lastNote[finger] = note;
		lastVoice[finger] = voice;
		// if the action is up, remove the upped finger from the lastvoice array
		if (type == RELEASE) {
			for (int i = finger; i < lastVoice.length - 1; i++) {
				lastVoice[finger] = lastVoice[finger + 1];
				lastNote[finger] = lastNote[finger + 1];
			}
		}
	}

	@SuppressLint("NewApi")
	private void keyPress(int voice, int note, float velocity) {
		if (holdButton.isChecked()) {
			if (!keyHeld[note]) {
				instrument.expression(0);
				instrument.keyPress(voice, note, velocity, false, true);
				if (instrument.mode != WaveSynth.MODE_POLYPHONIC) {
					clearHolds();
				}
				keyHeld[note] = true;
			} else {
				instrument.keyRelease(voice);
				keyHeld[note] = false;
			}
			showHolds();
		} else {
			instrument.expression(0);
			instrument.keyPress(voice, note, velocity, false, true);
		}
		getKeyForNote(note).setPressed(true);
	}

	@SuppressLint("NewApi")
	private void keyRelease(int voice, int note) {
		if (!holdButton.isChecked()) {
			instrument.keyRelease(voice);
		}
		getKeyForNote(note).setPressed(false);
	}

	private View getKeyForNote(int note) {
		if (note <= 0) {
			return key1;
		} else if (note == 1) {
			return key2;
		} else if (note == 2) {
			return key3;
		} else if (note == 3) {
			return key4;
		} else if (note == 4) {
			return key5;
		} else if (note == 5) {
			return key6;
		} else if (note == 6) {
			return key7;
		} else if (note == 7) {
			return key8;
		} else if (note == 8) {
			return key9;
		} else if (note == 9) {
			return key10;
		} else if (note == 10) {
			return key11;
		} else if (note == 11) {
			return key12;
		} else if (note == 12) {
			return key13;
		} else if (note == 13) {
			return key14;
		} else if (note == 14) {
			return key15;
		} else if (note == 15) {
			return key16;
		} else if (note == 16) {
			return key17;
		} else if (note == 17) {
			return key18;
		} else if (note == 18) {
			return key19;
		} else if (note == 19) {
			return key20;
		} else if (note == 20) {
			return key21;
		} else if (note == 21) {
			return key22;
		} else if (note == 22) {
			return key23;
		} else if (note == 23) {
			return key24;
		} else if (note == 24) {
			return key25;
		} else if (note == 25) {
			return key26;
		} else if (note == 26) {
			return key27;
		} else if (note == 27) {
			return key28;
		} else if (note == 28) {
			return key29;
		} else if (note == 29) {
			return key30;
		} else if (note == 30) {
			return key31;
		} else if (note == 31) {
			return key32;
		} else {
			return key32;
		}
	}

	private boolean isPointInsideView(float x, float y, View view) {
		if (view.getVisibility() == View.GONE) {
			return false;
		}
		int location[] = new int[2];
		view.getLocationOnScreen(location);
		int viewX = location[0];
		int viewY = location[1];

		// point is inside view bounds
		if ((x > viewX && x < (viewX + view.getWidth())) && (y > viewY && y < (viewY + view.getHeight()))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView == whiteNoiseCheckBox) {
			instrument.whiteNoiseAmount = isChecked ? 1.0f : 0.0f;
			instrument.updateParams();
		} else if (isChecked) {
			if (buttonView == monoRadio) {
				instrument.mode = 0;
			} else if (buttonView == polyRadio) {
				instrument.mode = 1;
			} else if (buttonView == chorusRadio) {
				instrument.mode = 2;
			} else if (buttonView == octave2Radio) {
				instrument.octave = 1;
			} else if (buttonView == octave3Radio) {
				instrument.octave = 2;
			} else if (buttonView == octave4Radio) {
				instrument.octave = 3;
			} else if (buttonView == octave5Radio) {
				instrument.octave = 4;
			} else if (buttonView == octave6Radio) {
				instrument.octave = 5;
			}
			instrument.updateParams();
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if (applyingASound) {
			return;
		}
		if (seekBar == chorusWidth) {
			instrument.chorusWidth = progress * progress / 10000.0f;
		} else if (seekBar == harmonic1) {
			updateWaves();
		} else if (seekBar == harmonic2) {
			updateWaves();
		} else if (seekBar == harmonic3) {
			updateWaves();
		} else if (seekBar == harmonic4) {
			updateWaves();
		} else if (seekBar == harmonic5) {
			updateWaves();
		} else if (seekBar == harmonic6) {
			updateWaves();
		} else if (seekBar == harmonic7) {
			updateWaves();
		} else if (seekBar == harmonic8) {
			updateWaves();
		} else if (seekBar == harmonic9) {
			updateWaves();
		} else if (seekBar == harmonic10) {
			updateWaves();
		} else if (seekBar == harmonic11) {
			updateWaves();
		} else if (seekBar == harmonic12) {
			updateWaves();
		} else if (seekBar == harmonic13) {
			updateWaves();
		} else if (seekBar == harmonic14) {
			updateWaves();
		} else if (seekBar == harmonic15) {
			updateWaves();
		} else if (seekBar == harmonic16) {
			updateWaves();
		} else if (seekBar == harmonic17) {
			updateWaves();
		} else if (seekBar == harmonic18) {
			updateWaves();
		} else if (seekBar == harmonic19) {
			updateWaves();
		} else if (seekBar == harmonic20) {
			updateWaves();
		} else if (seekBar == harmonic21) {
			updateWaves();
		} else if (seekBar == harmonic22) {
			updateWaves();
		} else if (seekBar == harmonic23) {
			updateWaves();
		} else if (seekBar == harmonic24) {
			updateWaves();
		} else if (seekBar == harmonic25) {
			updateWaves();
		} else if (seekBar == harmonic26) {
			updateWaves();
		} else if (seekBar == harmonic27) {
			updateWaves();
		} else if (seekBar == harmonic28) {
			updateWaves();
		} else if (seekBar == harmonic29) {
			updateWaves();
		} else if (seekBar == harmonic30) {
			updateWaves();
		} else if (seekBar == harmonic31) {
			updateWaves();
		} else if (seekBar == harmonic32) {
			updateWaves();
		} else if (seekBar == filterResonance) {
			instrument.filterResonance = progress / 100.0f;
			updateWaves();
		} else if (seekBar == filterLow) {
			instrument.filterLow = progress / 100.0f;
		} else if (seekBar == filterHigh) {
			instrument.filterHigh = progress / 100.0f;
		} else if (seekBar == filterEnvAttack) {
			instrument.filterEnvAttack = progress * progress / 50.0f;
		} else if (seekBar == filterEnvDecay) {
			instrument.filterEnvDecay = progress * progress / 50.0f;
		} else if (seekBar == filterEnvSustain) {
			instrument.filterEnvSustain = progress / 100.0f;
		} else if (seekBar == filterEnvRelease) {
			instrument.filterEnvRelease = progress * progress / 50.0f;
		} else if (seekBar == ampVolume) {
			instrument.ampVolume = progress / 100.0f;
		} else if (seekBar == ampAttack) {
			instrument.ampAttack = progress * progress / 50.0f;
		} else if (seekBar == ampDecay) {
			if (progress >= 99) {
				progress = 1000; // make it near infinite
			}
			instrument.ampDecay = progress * progress / 50.0f;
		} else if (seekBar == ampSustain) {
			instrument.ampSustain = progress / 100.0f;
		} else if (seekBar == ampRelease) {
			instrument.ampRelease = progress * progress / 50.0f;
		} else if (seekBar == ampOverdrive) {
			instrument.ampOverdrive = progress;
		} else if (seekBar == vibratoAmount) {
			instrument.vibratoAmount = progress * progress / 25000.0f;
		} else if (seekBar == vibratoRate) {
			instrument.vibratoRate = progress * progress * progress / 1000.0f + 1;
		} else if (seekBar == vibratoAttack) {
			instrument.vibratoAttack = progress * progress / 50.0f;
		} else if (seekBar == vibratoDecay) {
			if (progress >= 99) {
				progress = 1000; // make it near infinite
			}
			instrument.vibratoDecay = progress * progress / 50.0f;
		} else if (seekBar == vibrato2Amount) {
			instrument.vibrato2Amount = progress * progress / 25000.0f;
		} else if (seekBar == vibrato2Rate) {
			instrument.vibrato2Rate = progress * progress * progress / 1000.0f + 1;
		} else if (seekBar == vibrato2Attack) {
			instrument.vibrato2Attack = progress * progress / 50.0f;
		} else if (seekBar == vibrato2Decay) {
			if (progress >= 99) {
				progress = 1000; // make it near infinite
			}
			instrument.vibrato2Decay = progress * progress / 50.0f;
		} else if (seekBar == echoAmount) {
			instrument.echoAmount = progress / 100.0f;
		} else if (seekBar == echoDelay) {
			instrument.echoDelay = progress * progress / 10201.0f;
		} else if (seekBar == echoFeedback) {
			instrument.echoFeedback = progress / 101.0f; // avoid growing
		} else if (seekBar == flangeAmount) {
			instrument.flangeAmount = progress / 100.0f;
		} else if (seekBar == flangeRate) {
			instrument.flangeRate = progress / 100.0f;
		} else if (seekBar == distortionAmount) {
			instrument.distortionAmount = progress / 100.0f;
			instrument.updateParams();
		} else if (seekBar == bitCrush) {
			instrument.bitCrushAmount = progress / 100.0f;
			instrument.updateParams();
		} else if (seekBar == pinkNoiseAmount) {
			instrument.pinkNoiseAmount = progress / 100.0f;
			instrument.updateParams();
		} else if (seekBar == brownNoiseAmount) {
			instrument.brownNoiseAmount = progress / 100.0f;
			instrument.updateParams();
		} else if (seekBar == staticNoiseAmount) {
			instrument.staticNoiseAmount = progress / 100.0f;
			instrument.updateParams();
		} else if (seekBar == reverbAmount) {
			instrument.reverbAmount = progress / 100.0f;
			instrument.updateParams();
		} else if (seekBar == limiterAmount) {
			instrument.limiterAmount = progress / 100.0f;
			instrument.updateParams();
		} else if (seekBar == compressorAmount) {
			instrument.compressorAmount = progress / 100.0f;
			instrument.updateParams();
		} else if (seekBar == reverbRoomSize) {
			instrument.reverbRoomSize = progress / 100.0f;
			instrument.updateParams();
		} else if (seekBar == reverbDamping) {
			instrument.reverbDamping = progress / 100.0f;
			instrument.updateParams();
		} else if (seekBar == limiterThreshold) {
			instrument.limiterThreshold = progress / 100.0f;
			instrument.updateParams();
		} else if (seekBar == limiterRelease) {
			instrument.limiterRelease = progress / 100.0f;
			instrument.updateParams();
		} else if (seekBar == compressorRatio) {
			instrument.compressorRatio = progress / 100.0f;
			instrument.updateParams();
		} else if (seekBar == compressorAttack) {
			instrument.compressorAttack = progress / 100.0f;
			instrument.updateParams();
		} else if (seekBar == portamentoAmount) {
			instrument.portamentoAmount = progress / 101.0f;
		} else if (seekBar == sequence1) {
			updateSequence();
		} else if (seekBar == sequence2) {
			updateSequence();
		} else if (seekBar == sequence3) {
			updateSequence();
		} else if (seekBar == sequence4) {
			updateSequence();
		} else if (seekBar == sequence5) {
			updateSequence();
		} else if (seekBar == sequence6) {
			updateSequence();
		} else if (seekBar == sequence7) {
			updateSequence();
		} else if (seekBar == sequence8) {
			updateSequence();
		} else if (seekBar == sequence9) {
			updateSequence();
		} else if (seekBar == sequence10) {
			updateSequence();
		} else if (seekBar == sequence11) {
			updateSequence();
		} else if (seekBar == sequence12) {
			updateSequence();
		} else if (seekBar == sequence13) {
			updateSequence();
		} else if (seekBar == sequence14) {
			updateSequence();
		} else if (seekBar == sequence15) {
			updateSequence();
		} else if (seekBar == sequence16) {
			updateSequence();
			// } else if (seekBar == sequenceRate) {
			// synth.sequenceRate = progress;
		}
		instrument.updateParams();
		dirty = true;
	}

	private void updateWaves() {
		float[] harmonics = new float[32];
		harmonics[0] = harmonic1.getProgress() * harmonic1.getProgress() / 20000.0f;
		harmonics[1] = harmonic2.getProgress() * harmonic2.getProgress() / 20000.0f;
		harmonics[2] = harmonic3.getProgress() * harmonic3.getProgress() / 20000.0f;
		harmonics[3] = harmonic4.getProgress() * harmonic4.getProgress() / 20000.0f;
		harmonics[4] = harmonic5.getProgress() * harmonic5.getProgress() / 20000.0f;
		harmonics[5] = harmonic6.getProgress() * harmonic6.getProgress() / 20000.0f;
		harmonics[6] = harmonic7.getProgress() * harmonic7.getProgress() / 20000.0f;
		harmonics[7] = harmonic8.getProgress() * harmonic8.getProgress() / 20000.0f;
		harmonics[8] = harmonic9.getProgress() * harmonic9.getProgress() / 20000.0f;
		harmonics[9] = harmonic10.getProgress() * harmonic10.getProgress() / 20000.0f;
		harmonics[10] = harmonic11.getProgress() * harmonic11.getProgress() / 20000.0f;
		harmonics[11] = harmonic12.getProgress() * harmonic12.getProgress() / 20000.0f;
		harmonics[12] = harmonic13.getProgress() * harmonic13.getProgress() / 20000.0f;
		harmonics[13] = harmonic14.getProgress() * harmonic14.getProgress() / 20000.0f;
		harmonics[14] = harmonic15.getProgress() * harmonic15.getProgress() / 20000.0f;
		harmonics[15] = harmonic16.getProgress() * harmonic16.getProgress() / 20000.0f;
		harmonics[16] = harmonic17.getProgress() * harmonic17.getProgress() / 20000.0f;
		harmonics[17] = harmonic18.getProgress() * harmonic18.getProgress() / 20000.0f;
		harmonics[18] = harmonic19.getProgress() * harmonic19.getProgress() / 20000.0f;
		harmonics[19] = harmonic20.getProgress() * harmonic20.getProgress() / 20000.0f;
		harmonics[20] = harmonic21.getProgress() * harmonic21.getProgress() / 20000.0f;
		harmonics[21] = harmonic22.getProgress() * harmonic22.getProgress() / 20000.0f;
		harmonics[22] = harmonic23.getProgress() * harmonic23.getProgress() / 20000.0f;
		harmonics[23] = harmonic24.getProgress() * harmonic24.getProgress() / 20000.0f;
		harmonics[24] = harmonic25.getProgress() * harmonic25.getProgress() / 20000.0f;
		harmonics[25] = harmonic26.getProgress() * harmonic26.getProgress() / 20000.0f;
		harmonics[26] = harmonic27.getProgress() * harmonic27.getProgress() / 20000.0f;
		harmonics[27] = harmonic28.getProgress() * harmonic28.getProgress() / 20000.0f;
		harmonics[28] = harmonic29.getProgress() * harmonic29.getProgress() / 20000.0f;
		harmonics[29] = harmonic30.getProgress() * harmonic30.getProgress() / 20000.0f;
		harmonics[30] = harmonic31.getProgress() * harmonic31.getProgress() / 20000.0f;
		harmonics[31] = harmonic32.getProgress() * harmonic32.getProgress() / 20000.0f;
		instrument.harmonics = harmonics;
		instrument.genWaves();
	}

	public void updateSequence() {
		int[] sequence = new int[16];
		sequence[0] = sequence1.getProgress();
		sequence[1] = sequence2.getProgress();
		sequence[2] = sequence3.getProgress();
		sequence[3] = sequence4.getProgress();
		sequence[4] = sequence5.getProgress();
		sequence[5] = sequence6.getProgress();
		sequence[6] = sequence7.getProgress();
		sequence[7] = sequence8.getProgress();
		sequence[8] = sequence9.getProgress();
		sequence[9] = sequence10.getProgress();
		sequence[10] = sequence11.getProgress();
		sequence[11] = sequence12.getProgress();
		sequence[12] = sequence13.getProgress();
		sequence[13] = sequence14.getProgress();
		sequence[14] = sequence15.getProgress();
		sequence[15] = sequence16.getProgress();
		instrument.sequence = sequence;
		instrument.updateSequence();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}


	/*

	@Override
	public void onMidiNoteOn(MidiInputDevice sender, int cable, int channel, int midinote, int midivelocity) {
		if (myMidiChannel != 0 && channel != myMidiChannel) {
			return;
		}
		int note = midinote - 60 + 12;
		float velocity = FastMath.min(1.0f, (midivelocity + 1) / 64.0f);
		if (synth.mode == EasySynth.MODE_MONOPHONIC) {
			synth.keyPress(1, note, velocity, false, true);
			lastNote1 = note;
		} else if (synth.mode == EasySynth.MODE_CHORUS) {
			synth.keyPress(1, note, velocity, false, true);
			lastNote1 = note;
			lastNote2 = note;
			lastNote3 = note;
			lastNote4 = note;
		} else if (synth.mode == EasySynth.MODE_POLYPHONIC) {
			int voice = synth.getVoice(note);
			synth.keyPress(voice, note, velocity, false, true);
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
	public void onMidiNoteOff(MidiInputDevice sender, int cable, int channel, int midinote, int midivelocity) {
		if (myMidiChannel != 0 && channel != myMidiChannel) {
			return;
		}
		int note = midinote - 60 + 12;
		if (synth.mode == EasySynth.MODE_MONOPHONIC) {
			if (lastNote1 == note) {
				synth.keyRelease(1);
			}
		} else if (synth.mode == EasySynth.MODE_CHORUS) {
			if (lastNote1 == note) {
				synth.keyRelease(1);
				synth.keyRelease(2);
				synth.keyRelease(3);
				synth.keyRelease(4);
			}
		} else if (synth.mode == EasySynth.MODE_POLYPHONIC) {
			if (lastNote1 == note) {
				synth.keyRelease(1);
			} else if (lastNote2 == note) {
				synth.keyRelease(2);
			} else if (lastNote3 == note) {
				synth.keyRelease(3);
			} else if (lastNote4 == note) {
				synth.keyRelease(4);
			}
		}
	}

	@Override
	public void onMidiPolyphonicAftertouch(MidiInputDevice sender, int cable, int channel, int note, int pressure) {
		if (myMidiChannel != 0 && channel != myMidiChannel) {
			return;
		}
	}


	@Override
	public void onMidiChannelAftertouch(MidiInputDevice sender, int cable, int channel, int pressure) {
		if (myMidiChannel != 0 && channel != myMidiChannel) {
			return;
		}
		synth.expression(pressure / 128.0f);
	}

	@Override
	public void onMidiPitchWheel(MidiInputDevice sender, int cable, int channel, int amount) {
		if (myMidiChannel != 0 && channel != myMidiChannel) {
			return;
		}
		synth.pitchBend((amount - 8192) / 8192.0f / 4.0f);
	}

	 */

	// --- MIDI ----

	public void onDeviceAttached(String deviceName) {
	}

	public void onDeviceDetached(String deviceName) {
	}

	public void onProgramChange(int programNum) {
	}

	public void onControlChange(int function, int value) {
		if (function == 0) { // ?
		} else if (function == 1) { // modulation amount
			// float currentVibratoAmount = progress * progress / 25000.0f;
			instrument.expression(value / 128.0f);
		} else if (function == 3) { // chorus (pulse) width
			instrument.chorusWidth = value / 128.0f;
			updateControls();
		} else if (function == 7) { // overall volume
			AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			am.setStreamVolume(AudioManager.STREAM_MUSIC, (int) ((value / 128.0f) * am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)), 0);
		} else if (function == 10) { // pan
			// doesn't exist
		} else if (function == 14) { // amp level
			instrument.ampVolume = value / 128.0f;
			updateControls();
		} else if (function == 16) { // vibrato rate
			instrument.vibratoRate = value;
			updateControls();
		} else if (function == 17) { // tremelo rate (actually lfo2)
			// doesn't exist
		} else if (function == 18) { // vibrato amount
			instrument.vibratoAmount = value / 128.0f;
			updateControls();
		} else if (function == 22) { // tremelo amount (actually lfo2)
			// doesn't exist
		} else if (function == 28) { // filter sustain
			instrument.filterEnvSustain = value / 128.0f;
			updateControls();
		} else if (function == 29) { // filter release
			instrument.filterEnvRelease = value;
			updateControls();
		} else if (function == 31) { // amp sustain
			instrument.ampSustain = value / 128.0f;
			updateControls();
		} else if (function == 64) { // damper pedal
			if (value > 0) {
				instrument.ampRelease = instrument.ampDecay;
			} else {
				instrument.ampRelease = sound.ampRelease;
			}
		} else if (function == 70) {

		} else if (function == 71) { // filter resonance
			instrument.filterResonance = value / 128.0f;
			instrument.genWaves();
			updateControls();
		} else if (function == 72) { // amp release
			instrument.ampRelease = value;
			updateControls();
		} else if (function == 73) { // amp attack
			instrument.ampAttack = value;
			updateControls();
		} else if (function == 74) { // filter cutoff
			instrument.filterLow = value / 128.0f;
			updateControls();
		} else if (function == 75) { // amp decay
			instrument.ampDecay = value;
			updateControls();
		} else if (function == 76) { // vibrato rate
			instrument.vibratoRate = value / 128.0f;
			updateControls();
		} else if (function == 77) { // vibrato amount
			instrument.vibratoAmount = value / 128.0f;
			updateControls();
		} else if (function == 78) { // vibrato attack
			instrument.vibratoAttack = value;
			updateControls();
		} else if (function == 81) { // filter depth
			instrument.filterHigh = value / 128.0f;
			updateControls();
		} else if (function == 82) { // filter attack
			instrument.filterEnvAttack = value;
			updateControls();
		} else if (function == 83) { // filter decay
			instrument.filterEnvDecay = value;
			updateControls();
		} else if (function == 91) { // reverb amount
			instrument.echoFeedback = value / 128.0f;
			updateControls();
		} else if (function == 93) { // chorus amount
			instrument.echoAmount = value / 128.0f;
			updateControls();
		}

		// Special commands
		else if (function == 120) { // all sound off
			instrument.allSoundOff();
		} else if (function == 121) { // reset all controllers
			// not implemented
		} else if (function == 122) { // local control
			// not implemented
		} else if (function == 123) { // all notes off
			instrument.keyRelease(1);
			instrument.keyRelease(2);
			instrument.keyRelease(3);
			instrument.keyRelease(4);
		} else if (function == 124) { // omni mode off
			// not supported
		} else if (function == 125) { // omni mode on
			// the default
		} else if (function == 126) { // mono mode on
			instrument.keyRelease(1);
			instrument.keyRelease(2);
			instrument.keyRelease(3);
			instrument.keyRelease(4);
			monoRadio.setChecked(true);
			polyRadio.setChecked(false);
			chorusRadio.setChecked(false);
		} else if (function == 127) { // poly mode on
			instrument.keyRelease(1);
			instrument.keyRelease(2);
			instrument.keyRelease(3);
			instrument.keyRelease(4);
			polyRadio.setChecked(true);
			chorusRadio.setChecked(false);
			monoRadio.setChecked(false);
		}
		instrument.updateParams();
	}

	public void onTimingClock() {
	}

	public void onSysex(byte[] data) {
	}

	/**
	 * Override the back button to prompt for quit.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && !event.isAltPressed()) {
			final MessageDialog dialog = new MessageDialog(this, null, "Are you sure you want to quit?", new String[] { "Yes", "No" }, null);
			dialog.show();
			dialog.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface d) {
					int rc = dialog.getButtonPressed();
					if (rc == 0) {
						finish();
					}
				}
			});
			return true; // overriding the standard action handling
		}
		return false;
	}

	public void applyExtendedRange() {
		boolean extended = ClientModel.getClientModel().isExtendedSliderRange();
		int maxValue = extended ? 1000 : 100;
		
		// Set max for all seekbars
		filterResonance.setMax(maxValue);
		filterLow.setMax(maxValue);
		filterHigh.setMax(maxValue);
		filterEnvAttack.setMax(maxValue);
		filterEnvDecay.setMax(maxValue);
		filterEnvSustain.setMax(maxValue);
		filterEnvRelease.setMax(maxValue);
		ampVolume.setMax(maxValue);
		ampAttack.setMax(maxValue);
		ampDecay.setMax(maxValue);
		ampSustain.setMax(maxValue);
		ampRelease.setMax(maxValue);
		ampOverdrive.setMax(maxValue);
		vibratoAmount.setMax(maxValue);
		vibratoRate.setMax(maxValue);

		flangeRate.setMax(maxValue);
		flangeAmount.setMax(maxValue);
		chorusWidth.setMax(maxValue);
		
		// Harmonics
		harmonic1.setMax(maxValue);
		harmonic2.setMax(maxValue);
		harmonic3.setMax(maxValue);
		harmonic4.setMax(maxValue);
		harmonic5.setMax(maxValue);
		harmonic6.setMax(maxValue);
		harmonic7.setMax(maxValue);
		harmonic8.setMax(maxValue);
		harmonic9.setMax(maxValue);
		harmonic10.setMax(maxValue);
		harmonic11.setMax(maxValue);
		harmonic12.setMax(maxValue);
		harmonic13.setMax(maxValue);
		harmonic14.setMax(maxValue);
		harmonic15.setMax(maxValue);
		harmonic16.setMax(maxValue);
		harmonic17.setMax(maxValue);
		harmonic18.setMax(maxValue);
		harmonic19.setMax(maxValue);
		harmonic20.setMax(maxValue);
		harmonic21.setMax(maxValue);
		harmonic22.setMax(maxValue);
		harmonic23.setMax(maxValue);
		harmonic24.setMax(maxValue);
		harmonic25.setMax(maxValue);
		harmonic26.setMax(maxValue);
		harmonic27.setMax(maxValue);
		harmonic28.setMax(maxValue);
		harmonic29.setMax(maxValue);
		harmonic30.setMax(maxValue);
		harmonic31.setMax(maxValue);
		harmonic32.setMax(maxValue);
		
		// Reapply current sound to adjust slider positions
	applySound(instrument.generateSound());
}

	public void updateCpuOverlay() {
		if (clientModel.isShowCpuUsage()) {
			if (cpuOverlay != null) {
				cpuOverlay.setVisibility(View.VISIBLE);
			}
		} else {
			if (cpuOverlay != null) {
				cpuOverlay.setVisibility(View.GONE);
			}
			stopCpuMonitoring();
		}
	}

	private void startCpuMonitoring() {
		if (!clientModel.isShowCpuUsage()) {
			return;
		}
		
		stopCpuMonitoring();
		
		cpuMonitorThread = new Thread(new Runnable() {
			@Override
			public void run() {
				long lastCpuTime = 0;
				long lastAppTime = android.os.SystemClock.elapsedRealtime();
				
				while (!Thread.currentThread().isInterrupted()) {
					try {
						Thread.sleep(500);
						
						long currentCpuTime = android.os.Debug.threadCpuTimeNanos();
						long currentAppTime = android.os.SystemClock.elapsedRealtime();
						
						if (lastCpuTime > 0) {
							long cpuDelta = currentCpuTime - lastCpuTime;
							long timeDelta = (currentAppTime - lastAppTime) * 1000000;
							
							if (timeDelta > 0) {
								final double cpuUsage = (cpuDelta * 100.0) / timeDelta;
								
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										if (cpuOverlay != null) {
											cpuOverlay.setText(String.format("CPU: %.1f%%", cpuUsage));
										}
									}
								});
							}
						}
						
						lastCpuTime = currentCpuTime;
						lastAppTime = currentAppTime;
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		});
		cpuMonitorThread.start();
	}

	private void stopCpuMonitoring() {
		if (cpuMonitorThread != null) {
			cpuMonitorThread.interrupt();
			cpuMonitorThread = null;
		}
	}
}

