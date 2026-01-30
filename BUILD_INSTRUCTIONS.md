# How to Build WaveSynth

This document explains how to build the WaveSynth Android app yourself.

## Prerequisites

Before building the app, make sure you have:

1. **Java Development Kit (JDK) 8 or higher**
   - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
   - Verify installation: `java -version`

2. **Android SDK** (included with Android Studio)
   - Minimum SDK: API 28 (Android 9.0)
   - Target SDK: API 35 (Android 15)
   - Build Tools: Latest version

3. **Android NDK** (for native code compilation)
   - Version: 23.0.7599858 (or compatible)
   - Automatically downloaded by Gradle if missing

## Method 1: Command Line Build (Recommended)

### Windows:

1. Open PowerShell or Command Prompt
2. Navigate to the project directory:
   ```powershell
   cd C:\Users\Yerasyl\Desktop\WaveSynth
   ```

3. Build the debug APK:
   ```powershell
   .\gradlew assembleDebug
   ```

4. Build the release APK (unsigned):
   ```powershell
   .\gradlew assembleRelease
   ```

### Linux/Mac:

1. Open Terminal
2. Navigate to the project directory:
   ```bash
   cd /path/to/WaveSynth
   ```

3. Make gradlew executable (first time only):
   ```bash
   chmod +x gradlew
   ```

4. Build the debug APK:
   ```bash
   ./gradlew assembleDebug
   ```

5. Build the release APK (unsigned):
   ```bash
   ./gradlew assembleRelease
   ```

## Method 2: Android Studio

1. **Open Project:**
   - Launch Android Studio
   - Click "Open" and select the `WaveSynth` folder
   - Wait for Gradle sync to complete

2. **Build APK:**
   - Menu: `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
   - Or use the shortcut: `Ctrl+F9` (Windows/Linux) or `Cmd+F9` (Mac)

3. **Build and Install:**
   - Connect an Android device via USB (with USB debugging enabled)
   - Or start an Android emulator
   - Click the green "Run" button (▶) or press `Shift+F10`

## Output Location

After a successful build, the APK files will be located at:

- **Debug APK:** `app/build/outputs/apk/debug/app-debug.apk`
- **Release APK:** `app/build/outputs/apk/release/app-release-unsigned.apk`

The debug APK is **immediately installable** on any Android device. The release APK needs to be signed before distribution.

## Installing the APK

### On Android Device:

1. Copy the APK file to your device
2. Open the APK file using a file manager
3. Allow installation from unknown sources if prompted
4. Follow the installation wizard

### Using ADB (Android Debug Bridge):

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Cleaning the Build

If you encounter build issues, try cleaning the project:

```bash
# Windows
.\gradlew clean

# Linux/Mac
./gradlew clean
```

Then rebuild using the commands above.

## Troubleshooting

### "Command not found: gradlew"
- Make sure you're in the project root directory
- On Linux/Mac, make gradlew executable: `chmod +x gradlew`

### "SDK location not found"
- Create a `local.properties` file in the project root:
  ```properties
  sdk.dir=C:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
  ```
  (Adjust the path to your Android SDK location)

### "NDK not found"
- Gradle will automatically download the NDK if missing
- Or manually download NDK 23.0.7599858 from Android Studio's SDK Manager

### Build fails with Java version errors:
- The project uses Java 8 compatibility
- If you have JDK 21+, you'll see deprecation warnings (safe to ignore)
- Or add to `gradle.properties`:
  ```properties
  android.javaCompile.suppressSourceTargetDeprecationWarning=true
  ```

## New Features in This Build

✅ **Extended Slider Range** - Toggle 10x multiplier for all synthesizer controls  
✅ **Recording Settings** - Sample rate (22kHz - 96kHz) and bit depth (16/24/32-bit) options  
✅ **CPU Usage Overlay** - Real-time CPU usage display (enable in settings)  
✅ **Factory Reset** - Complete settings reset with 5-level confirmation + math challenge

## Build Variants

- **Debug:** Includes debugging symbols, not optimized
- **Release:** Optimized for production, requires signing

For distribution, use the release variant and sign it with your keystore.

## Questions?

If you encounter any issues not covered here, check:
- Gradle version: 8.13
- Android Gradle Plugin: 8.7.3
- Make sure all dependencies download successfully

Happy building! 🎵
