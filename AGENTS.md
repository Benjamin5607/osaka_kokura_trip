# AGENTS.md

## Cursor Cloud specific instructions

This repo is a single-module **Android** app (`:app`) — Kotlin + Jetpack Compose trip planner (`OsakaKitakyushuTrip`). There is no backend; data is local Room SQLite seeded from Kotlin constants.

### Toolchain (already on the Cloud VM image)

- JDK 21 is fine (project compiles with Java 11 bytecode).
- `ANDROID_HOME` / `ANDROID_SDK_ROOT` → `/opt/android-sdk` (platform 36, build-tools 36, platform-tools, emulator).
- Gradle via `./gradlew` (wrapper must be present; distribution **9.3.1**).

### One-time local files (not committed)

```bash
echo "sdk.dir=$ANDROID_HOME" > local.properties
keytool -genkeypair -keystore debug.keystore -storepass android -alias androiddebugkey \
  -keypass android -keyalg RSA -keysize 2048 -validity 10000 \
  -dname "CN=Android Debug,O=Android,C=US"
```

`app/build.gradle.kts` signs debug builds with root `debug.keystore` (gitignored). Without it, `:app:assembleDebug` fails at signing.

`.env` is optional; `.env.example` is enough (Gemini key unused in app code today). Missing `google-services.json` is expected (`missingGoogleServicesStrategy = WARN`).

### Commands

| Action | Command |
|--------|---------|
| Unit / Robolectric / Roborazzi tests | `./gradlew :app:testDebugUnitTest` |
| Lint | `./gradlew :app:lintDebug` |
| Debug APK | `./gradlew :app:assembleDebug` |
| Install on device | `adb install -r app/build/outputs/apk/debug/app-debug.apk` |
| Launch | `adb shell am start -n com.aistudio.osakakitakyushu.jptrip/com.example.MainActivity` |

Instrumented `androidTest` needs a device/emulator; JVM unit tests do not.

### Emulator caveats (Cloud Agent VM)

- Nested KVM **does not work** here (`kvm_arch_vcpu_create` kernel BUG). Always start with **`-accel off`** (software TCG). Do **not** use `-accel on` / KVM.
- Prefer headless: `emulator -avd trip_api34 -no-window -no-audio -no-boot-anim -gpu swiftshader_indirect -accel off`.
- Cold boot with TCG can take **~8–10+ minutes**. ANRs are common under load; dismiss and retry taps slowly.
- AVD name used in setup: `trip_api34` (`system-images;android-34;google_apis;x86_64`). Recreate with `avdmanager` if missing.
- `/dev/kvm` may need `chmod 666` or membership in group `kvm`; it still will not accelerate guests in this environment.

### Product notes

Bottom tabs: 일정표 · 교통&패스 · 현지맛집 · 돌발·SOS · 체크리스트. Core offline flows (toggle schedule done, checklist CRUD) hit Room — good hello-world targets without network or Gemini.
