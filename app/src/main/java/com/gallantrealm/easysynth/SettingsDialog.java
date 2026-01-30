package com.gallantrealm.easysynth;

import com.gallantrealm.android.Translator;
import com.gallantrealm.easysynth.theme.AuraTheme;
import com.gallantrealm.easysynth.theme.CrystalTheme;
import com.gallantrealm.easysynth.theme.CustomTheme;
import com.gallantrealm.easysynth.theme.DesertTheme;
import com.gallantrealm.easysynth.theme.ForestTheme;
import com.gallantrealm.easysynth.theme.GalaxyTheme;
import com.gallantrealm.easysynth.theme.IceTheme;
import com.gallantrealm.easysynth.theme.MetalTheme;
import com.gallantrealm.easysynth.theme.NeonTheme;
import com.gallantrealm.easysynth.theme.OceanTheme;
import com.gallantrealm.easysynth.theme.RetroTheme;
import com.gallantrealm.easysynth.theme.SpaceTheme;
import com.gallantrealm.easysynth.ClientModel;
import com.gallantrealm.android.SelectItemDialog;
import com.gallantrealm.android.MessageDialog;
import com.gallantrealm.android.InputDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import de.viktorreiser.toolbox.widget.NumberPicker;

public class SettingsDialog extends Dialog {
	ClientModel clientModel = ClientModel.getClientModel();

	Spinner keysSpinner;
	Spinner midiChannelSpinner;
	Spinner tuningSpinner;
	NumberPicker tuningCents;
	CheckBox extendedRangeCheckbox;
	Spinner sampleRateSpinner;
	Spinner bitDepthSpinner;
	CheckBox showCpuCheckbox;
	Button factoryResetButton;
	Button okButton;
	Button chooseBackgroundButton;
	Button aboutButton;
	int buttonPressed = -1;
	String title;
	String message;
	String initialValue;
	String[] options;
	MainActivity activity;

	public SettingsDialog(MainActivity context) {
		super(context, R.style.Theme_Dialog);
		activity = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.title = "Settings";
		this.message = "test";
		this.initialValue = "";
		this.options = null;
		setContentView(R.layout.settings_dialog);
		setCancelable(false);
		setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		keysSpinner = (Spinner) findViewById(R.id.keysSpinner);
		midiChannelSpinner = (Spinner) findViewById(R.id.midiChannelSpinner);
		tuningSpinner = (Spinner) findViewById(R.id.tuningSpinner);
		tuningCents = (NumberPicker)findViewById(R.id.tuningCents);
	extendedRangeCheckbox = (CheckBox) findViewById(R.id.extendedRangeCheckbox);
	sampleRateSpinner = (Spinner) findViewById(R.id.sampleRateSpinner);
	bitDepthSpinner = (Spinner) findViewById(R.id.bitDepthSpinner);
	showCpuCheckbox = (CheckBox) findViewById(R.id.showCpuCheckbox);
	factoryResetButton = (Button) findViewById(R.id.factoryResetButton);
	okButton = (Button) findViewById(R.id.okButton);
	chooseBackgroundButton = (Button) findViewById(R.id.chooseBackgroundButton);
	aboutButton = (Button) findViewById(R.id.aboutButton);

	Typeface typeface = clientModel.getTypeface(getContext());
		if (typeface != null) {
			okButton.setTypeface(typeface);
			chooseBackgroundButton.setTypeface(typeface);
		}

		ArrayAdapter<CharSequence> keysAdapter = Translator.getArrayAdapter(activity, R.layout.spinner_item, new String[] { "13", "20", "25", "32" });
		keysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		keysSpinner.setAdapter(keysAdapter);
		keysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView av, View v, int arg2, long arg3) {
				int keysSelection = keysSpinner.getSelectedItemPosition();
				activity.setKeyboardSize(keysSelection);
				clientModel.setKeyboardSize(keysSelection);
				clientModel.savePreferences(activity);
			}

			@Override
			public void onNothingSelected(AdapterView av) {
			}
		});
		int keySelection = clientModel.getKeyboardSize();
		keysSpinner.setSelection(keySelection);
		
		ArrayAdapter<CharSequence> midiChannelAdapter = Translator.getArrayAdapter(activity, R.layout.spinner_item, new String[] { "Any", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" });
		midiChannelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		midiChannelSpinner.setAdapter(midiChannelAdapter);
		midiChannelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView av, View v, int arg2, long arg3) {
				int midiChannel = midiChannelSpinner.getSelectedItemPosition();
				activity.setMidiChannel(midiChannel);
				clientModel.setMidiChannel(midiChannel);
				clientModel.savePreferences(activity);
			}

			@Override
			public void onNothingSelected(AdapterView av) {
			}
		});
		midiChannelSpinner.setSelection(clientModel.getMidiChannel());

		ArrayAdapter<CharSequence> tuningAdapter = Translator.getArrayAdapter(activity, R.layout.spinner_item, new String[] { "Equal Temperament", "Just Intonation", "Out of Tune" });
		tuningAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tuningSpinner.setAdapter(tuningAdapter);
		tuningSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView av, View v, int arg2, long arg3) {
				int tuningSelection = tuningSpinner.getSelectedItemPosition();
				activity.setTuning(tuningSelection);
				clientModel.setSampleRateReducer(tuningSelection);
				clientModel.savePreferences(activity);
			}

			@Override
			public void onNothingSelected(AdapterView av) {
			}
		});
		int tuningSelection = clientModel.getSampleRateReducer();
		tuningSpinner.setSelection(tuningSelection);

		tuningCents.setCurrent(clientModel.getTuningCents());
		tuningCents.setOnChangeListener(new NumberPicker.OnChangedListener() {
			public void onChanged(NumberPicker picker, int oldVal, int newVal) {
				activity.setTuningCents(tuningCents.getCurrent());
				clientModel.setTuningCents(tuningCents.getCurrent());
				clientModel.savePreferences(activity);
			}
		});

	extendedRangeCheckbox.setChecked(clientModel.isExtendedSliderRange());
	extendedRangeCheckbox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
			clientModel.setExtendedSliderRange(isChecked);
			clientModel.savePreferences(activity);
			activity.applyExtendedRange();
		}
	});

	ArrayAdapter<CharSequence> sampleRateAdapter = Translator.getArrayAdapter(activity, R.layout.spinner_item, new String[] { "Native (~44.1kHz)", "22050 Hz", "44100 Hz", "48000 Hz", "96000 Hz" });
	sampleRateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	sampleRateSpinner.setAdapter(sampleRateAdapter);
	sampleRateSpinner.setSelection(clientModel.getRecordingSampleRate());
	sampleRateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView av, View v, int arg2, long arg3) {
			int sampleRateSelection = sampleRateSpinner.getSelectedItemPosition();
			clientModel.setRecordingSampleRate(sampleRateSelection);
			clientModel.savePreferences(activity);
		}

		@Override
		public void onNothingSelected(AdapterView av) {
		}
	});

	ArrayAdapter<CharSequence> bitDepthAdapter = Translator.getArrayAdapter(activity, R.layout.spinner_item, new String[] { "16-bit", "24-bit", "32-bit float" });
	bitDepthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	bitDepthSpinner.setAdapter(bitDepthAdapter);
	bitDepthSpinner.setSelection(clientModel.getRecordingBitDepth());
	bitDepthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView av, View v, int arg2, long arg3) {
			int bitDepthSelection = bitDepthSpinner.getSelectedItemPosition();
			clientModel.setRecordingBitDepth(bitDepthSelection);
			clientModel.savePreferences(activity);
		}

		@Override
		public void onNothingSelected(AdapterView av) {
		}
	});

	showCpuCheckbox.setChecked(clientModel.isShowCpuUsage());
	showCpuCheckbox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
			clientModel.setShowCpuUsage(isChecked);
			clientModel.savePreferences(activity);
			activity.updateCpuOverlay();
		}
	});

	factoryResetButton.setOnTouchListener(new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				startFactoryReset();
			}
			return true;
		}
	});

okButton.setOnTouchListener(new OnTouchListener() {

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		buttonPressed = 0;
		SettingsDialog.this.dismiss();
		SettingsDialog.this.cancel();
		return true;
	}

});
chooseBackgroundButton.setOnTouchListener(new OnTouchListener() {

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			chooseBackground();
		}
		return true;
	}

});
	
	// About button listener
	if (aboutButton != null) {
		aboutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showAboutDialog();
			}
		});
	}

	Translator.getTranslator().translate(this.getWindow().getDecorView());}

@Override
public void show() {
	super.show();
}
	@Override
	public void dismiss() {
		super.dismiss();
	}
	
	private void openUrl(String url) {
		try {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			activity.startActivity(browserIntent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showAboutDialog() {
		final android.app.Dialog aboutDialog = new android.app.Dialog(activity, R.style.Theme_Dialog);
		aboutDialog.setContentView(R.layout.about_dialog);
		aboutDialog.setTitle("About");
		
		// Setup social media buttons
		android.widget.ImageButton twitterBtn = aboutDialog.findViewById(R.id.twitterButton);
		android.widget.ImageButton instagramBtn = aboutDialog.findViewById(R.id.instagramButton);
		android.widget.ImageButton discordBtn = aboutDialog.findViewById(R.id.discordButton);
		android.widget.ImageButton githubBtn = aboutDialog.findViewById(R.id.githubButton);
		
		if (twitterBtn != null) {
			twitterBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					openUrl("https://x.com/@naii_dev");
				}
			});
		}
		
		if (instagramBtn != null) {
			instagramBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					openUrl("https://instagram.com/@naii_dev");
				}
			});
		}
		
		if (discordBtn != null) {
			discordBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					openUrl("https://discord.gg/uEMrweE8HA");
				}
			});
		}
		
		if (githubBtn != null) {
			githubBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					openUrl("https://github.com/Nai64");
				}
			});
		}
		
		aboutDialog.show();
	}

	public void chooseBackground() {
		// Get list of background images from assets/backgrounds folder
		final java.util.ArrayList<String> backgroundList = new java.util.ArrayList<>();
		try {
			String[] files = activity.getAssets().list("backgrounds");
			if (files != null) {
				for (String file : files) {
					if (file.toLowerCase().endsWith(".png") || file.toLowerCase().endsWith(".jpg")) {
						// Remove extension for display name
						String name = file.substring(0, file.lastIndexOf('.'));
						backgroundList.add(name);
					}
				}
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		
		// Sort alphabetically
		java.util.Collections.sort(backgroundList);
		
		// Add Custom option if full version
		if (Build.VERSION.SDK_INT >= 16 && (ClientModel.getClientModel().isFullVersion() || ClientModel.getClientModel().isGoggleDogPass())) {
			backgroundList.add("Custom");
		}
		
		final String[] backgroundArray = backgroundList.toArray(new String[0]);
		
		// Create custom GridView dialog with thumbnails
		final android.app.Dialog dialog = new android.app.Dialog(activity, R.style.Theme_Dialog);
		dialog.setContentView(R.layout.background_grid_dialog);
		dialog.setTitle("Choose a background");
		
		// Make dialog wider
		if (dialog.getWindow() != null) {
			android.view.WindowManager.LayoutParams lp = new android.view.WindowManager.LayoutParams();
			lp.copyFrom(dialog.getWindow().getAttributes());
			lp.width = (int)(activity.getResources().getDisplayMetrics().widthPixels * 0.95);
			dialog.getWindow().setAttributes(lp);
		}
		
		android.widget.GridView gridView = dialog.findViewById(R.id.backgroundGrid);
		gridView.setNumColumns(4);
		
		android.widget.BaseAdapter adapter = new android.widget.BaseAdapter() {
			@Override
			public int getCount() {
				return backgroundArray.length;
			}
			
			@Override
			public Object getItem(int position) {
				return backgroundArray[position];
			}
			
			@Override
			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
				if (convertView == null) {
					convertView = activity.getLayoutInflater().inflate(R.layout.background_item, parent, false);
				}
				
				android.widget.ImageView thumbnail = convertView.findViewById(R.id.thumbnail);
				android.widget.TextView name = convertView.findViewById(R.id.name);
				
				String backgroundName = backgroundArray[position];
				name.setText(backgroundName);
				
				// Load thumbnail in background thread
				final android.view.View finalView = convertView;
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							java.io.InputStream is;
							if (backgroundName.equals("Custom")) {
								is = activity.getAssets().open("backgrounds/Onyx.png");
							} else {
								try {
									is = activity.getAssets().open("backgrounds/" + backgroundName + ".png");
								} catch (java.io.IOException ex) {
									is = activity.getAssets().open("backgrounds/" + backgroundName + ".jpg");
								}
							}
							final android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeStream(is);
							is.close();
							
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									thumbnail.setImageBitmap(bitmap);
								}
							});
						} catch (java.io.IOException e) {
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									thumbnail.setImageResource(R.raw.onyx_background);
								}
							});
						}
					}
				}).start();
				
				return convertView;
			}
		};
		
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
				String option = backgroundArray[position];
				clientModel.setBackgroundName(option);
				clientModel.savePreferences(getContext());
				activity.setTheme(option, null);
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}

	private void startFactoryReset() {
		// Level 1
		final MessageDialog level1 = new MessageDialog(activity, "Factory Reset", "Are you sure you want to reset all settings?", new String[] { "Continue", "Cancel" });
		level1.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				if (level1.getButtonPressed() == 0) {
					showLevel2();
				}
			}
		});
		level1.show();
	}

	private void showLevel2() {
		final MessageDialog level2 = new MessageDialog(activity, "Warning!", "This will delete ALL your custom sounds and settings. Continue?", new String[] { "Continue", "Cancel" });
		level2.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				if (level2.getButtonPressed() == 0) {
					showLevel3();
				}
			}
		});
		level2.show();
	}

	private void showLevel3() {
		final MessageDialog level3 = new MessageDialog(activity, "No Undo!", "There is NO UNDO. Really proceed?", new String[] { "Continue", "Cancel" });
		level3.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				if (level3.getButtonPressed() == 0) {
					showLevel4();
				}
			}
		});
		level3.show();
	}

	private void showLevel4() {
		final MessageDialog level4 = new MessageDialog(activity, "Last Warning!", "Last warning! This action is IRREVERSIBLE!", new String[] { "Continue", "Cancel" });
		level4.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				if (level4.getButtonPressed() == 0) {
					showMathChallenge();
				}
			}
		});
		level4.show();
	}

	private void showMathChallenge() {
		final InputDialog mathDialog = new InputDialog(activity, "Final Confirmation", "Solve this to confirm: What is 7 + 15?", "", null);
		mathDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				String answer = mathDialog.getValue();
				if (answer != null && answer.trim().equals("22")) {
					performFactoryReset();
				} else if (answer != null && !answer.isEmpty()) {
					android.widget.Toast.makeText(activity, "Wrong answer. Factory reset cancelled.", android.widget.Toast.LENGTH_SHORT).show();
				}
			}
		});
		mathDialog.show();
	}

	private void performFactoryReset() {
		clientModel.clearPreferences(activity);
		android.widget.Toast.makeText(activity, "All settings and data have been reset!", android.widget.Toast.LENGTH_LONG).show();
		SettingsDialog.this.dismiss();
		activity.recreate();
	}

}

