# WaveSynth Build Variants

This project now supports **3 different build variants** that can be built simultaneously:

## 🎯 Available Variants

### 1. **Full Version** (Original)
- **Package Name:** `com.gallantrealm.easysynth.full`
- **Features:** All features enabled, includes background images
- **Target:** Modern devices (Android 9.0+, API 28+)
- **Best for:** Users who want the complete experience

### 2. **Lite Version** (No Backgrounds)
- **Package Name:** `com.gallantrealm.easysynth.lite`
- **Features:** All functionality, simplified graphics (no heavy background images)
- **Target:** Modern devices with limited storage
- **Best for:** Smaller APK size, faster downloads

### 3. **Legacy Version** (Older Devices)
- **Package Name:** `com.gallantrealm.easysynth.legacy`
- **Features:** Full features with compatibility mode
- **Target:** Older devices (Android 5.0+, API 21+)
- **Best for:** Users with older Android devices

---

## 🔨 Building Variants

### Build All Variants at Once
```bash
# Windows
build-all-variants.bat

# Linux/Mac
./gradlew assembleFullRelease assembleLiteRelease assembleLegacyRelease
```

### Build Individual Variants

**Full Version:**
```bash
./gradlew assembleFullDebug        # Debug build
./gradlew assembleFullRelease      # Release build
```

**Lite Version:**
```bash
./gradlew assembleLiteDebug        # Debug build
./gradlew assembleLiteRelease      # Release build
```

**Legacy Version:**
```bash
./gradlew assembleLegacyDebug      # Debug build
./gradlew assembleLegacyRelease    # Release build
```

### Output Locations
APK files will be generated in:
```
app/build/outputs/apk/
├── full/
│   ├── debug/app-full-debug.apk
│   └── release/app-full-release-unsigned.apk
├── lite/
│   ├── debug/app-lite-debug.apk
│   └── release/app-lite-release-unsigned.apk
└── legacy/
    ├── debug/app-legacy-debug.apk
    └── release/app-legacy-release-unsigned.apk
```

---

## 🎨 Customizing Variants

### Adding Variant-Specific Resources

Create flavor-specific resource directories:
```
app/src/
├── full/          # Full version only
│   └── res/
├── lite/          # Lite version only (simplified resources)
│   └── res/
│       └── drawable-nodpi/  # Replace background images with simple colors
└── legacy/        # Legacy version only
    └── res/
```

### Using BuildConfig in Code

The build configuration exposes these flags:
```java
// Check which variant is running
if (BuildConfig.ENABLE_BACKGROUNDS) {
    // Load background images (full/legacy)
} else {
    // Use solid colors (lite)
}

if (BuildConfig.LEGACY_COMPATIBILITY) {
    // Apply compatibility fixes for older devices
}
```

### Modifying build.gradle

Edit `app/build.gradle` to change variant configurations:
```gradle
productFlavors {
    full {
        applicationIdSuffix ".full"
        buildConfigField "boolean", "ENABLE_BACKGROUNDS", "true"
    }
    lite {
        applicationIdSuffix ".lite"
        buildConfigField "boolean", "ENABLE_BACKGROUNDS", "false"
    }
    legacy {
        minSdk 21  // Lower minimum SDK
        buildConfigField "boolean", "LEGACY_COMPATIBILITY", "true"
    }
}
```

---

## 📦 Installation

All 3 variants can be **installed on the same device simultaneously** because they have different package names:
- `com.gallantrealm.easysynth.full`
- `com.gallantrealm.easysynth.lite`
- `com.gallantrealm.easysynth.legacy`

This allows users to test different versions side by side.

---

## 🚀 Distribution

### Google Play Console
Upload all 3 APKs to different tracks:
- **Full Version** → Production track (main release)
- **Lite Version** → Production track (alternative for low-storage devices)
- **Legacy Version** → Production track (targets older API levels)

Google Play will automatically serve the appropriate version based on device compatibility.

### GitHub Releases
Tag and upload all variants:
```bash
git tag -a v10.7-full -m "WaveSynth 10.7 - Full Version"
git tag -a v10.7-lite -m "WaveSynth 10.7 - Lite Version"
git tag -a v10.7-legacy -m "WaveSynth 10.7 - Legacy Version"
git push --tags
```

---

## 🔧 Development

### Switching Variants in Android Studio
1. Open **Build Variants** panel (View → Tool Windows → Build Variants)
2. Select variant from dropdown:
   - `fullDebug` / `fullRelease`
   - `liteDebug` / `liteRelease`
   - `legacyDebug` / `legacyRelease`

### Running on Device
```bash
# Install specific variant
./gradlew installFullDebug
./gradlew installLiteDebug
./gradlew installLegacyDebug
```

---

## 📊 Variant Comparison

| Feature | Full | Lite | Legacy |
|---------|------|------|--------|
| Minimum Android | 9.0 (API 28) | 9.0 (API 28) | 5.0 (API 21) |
| Background Images | ✅ Yes | ❌ No | ✅ Yes |
| APK Size | ~15 MB | ~8 MB | ~15 MB |
| All Features | ✅ Yes | ✅ Yes | ✅ Yes |
| Compatibility Mode | ❌ No | ❌ No | ✅ Yes |
| Package Suffix | `.full` | `.lite` | `.legacy` |

---

## 💡 Tips

- **For most users:** Use **Full** version
- **For limited storage:** Use **Lite** version
- **For old devices (Android 5-8):** Use **Legacy** version
- **For testing:** Install all 3 variants simultaneously to compare
