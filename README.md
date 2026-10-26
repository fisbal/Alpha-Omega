# Alpha & Omega — New Testament Study

A deep, bilingual (EN/FR) New Testament study app — KJV/WEB and Louis Segond/Darby,
verse-by-verse commentary, and an honest investigation into the historical Jesus.

The app lives in `app/` (one `index.html` plus manifest, service worker and icons). The rest of the
repository builds it for every platform the same way as NavalOS, MillionOS and ProDFOS: every push to
`main` publishes the web version and a Release with the Android, Windows and Mac apps.

## Get the app

- **Web** (any phone, tablet or computer): https://fisbal.github.io/alpha-omega/ — installable from the browser and works offline.
- **Android, Windows, Mac**: the [latest release](https://github.com/fisbal/alpha-omega/releases/latest) has `AlphaOmega.apk`, `AlphaOmega-Setup.exe` (or `.msi`) and `AlphaOmega.dmg`. Installed apps update themselves: Windows and Mac install the new version and reopen, Android offers it.

## ✦ Ask Claude
The ✦ Claude tab opens a study companion for the passage on screen (words, background, cross-references, what scholars think). It uses the reader's own Claude key from console.anthropic.com, kept on the device only.

## How the automatic build works
Every pull request and every push to `main` runs `.github/workflows/build.yml`:
- `web` checks every screen on phone, tablet, PC and Mac sizes in English and French (`npm run check:screens`); on `main` it publishes `app/` to the `gh-pages` branch (Settings → Pages → Deploy from branch → `gh-pages`, once).
- `android` builds the APK from `android/`, which bundles `app/` and works offline, signed with the key in the `ALPHAOMEGA_KEYSTORE_B64` / `ALPHAOMEGA_KEYSTORE_PASSWORD` secrets so every version installs over the last.
- `windows` and `mac` build the desktop apps from `src-tauri/` (Tauri), with signed updates (`TAURI_SIGNING_*` secrets).
- `release` (on `main`) publishes Release v1.0.N with all of them, `latest.json` for the desktop updater and the source code.

## Repository layout

```
app/          the Alpha & Omega app itself (index.html, manifest, service worker, icons, vendor/ for Ask Claude)
android/      Android app around app/ (no app content here)
src-tauri/    Windows and Mac app around app/ (no app content here)
scripts/      screen check
```
