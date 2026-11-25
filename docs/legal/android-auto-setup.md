# Android Auto Setup

PPBrowser is installed through a private Google Play testing track and isn't an approved Android Auto app, so Android Auto may hide it until you allow apps from unknown sources. You only need to do this once, on the phone. The same checklist is in the app under **Settings > Android Auto and help > Android Auto setup**, where each step shows whether it's done and has a button that opens the right settings page.

## What you need

- A phone running **Android 15 or later**.
- The **Android Auto** app, up to date from the Play Store.
- A car or head unit with Android Auto, wired or wireless.
- PPBrowser installed from Google Play (through the private testing link), in your **main** phone profile. Android Auto doesn't see apps installed in a cloned-app space, a second space or a work profile.

## Step 1: Install PPBrowser

On the phone, open the private Google Play testing link you were given, signed in with the Google account that was added as a tester. Accept the invitation, then install PPBrowser from Google Play.

The first time PPBrowser opens, it asks you to accept the Terms of Use and Privacy Policy, then offers this setup checklist.

## Step 2: Allow apps from outside the Play Store in Android Auto

1. Open phone **Settings**, search for **Android Auto** and open it.
2. Scroll to the bottom and tap **Version** ten times. Tap **OK** when Android Auto asks about developer settings.
3. Open the **three-dot menu** in the top-right corner and choose **Developer settings**.
4. Tick **Unknown sources**.

In PPBrowser, **Settings > Android Auto setup > Open Android Auto settings** takes you straight to step 1.

## Step 3: Make sure PPBrowser is in the car launcher

In Android Auto's settings, open **Customize launcher** and make sure **PPBrowser** is ticked. PPBrowser also shows up among the media apps, which is the part you use for listening while driving.

## Step 4: Phone settings that keep audio playing

- **Notifications:** allow them. The playback controls and steering-wheel buttons depend on them.
- **Battery:** set PPBrowser to **Unrestricted**, or **Don't optimize**, so the phone doesn't stop audio a few minutes after Maps covers the browser.
  - OnePlus, OPPO and realme: App info > Battery > **Unrestricted** (and allow background activity).
  - Samsung: App info > Battery > **Unrestricted**. Also check that PPBrowser isn't in **Sleeping apps**.
  - Xiaomi, Redmi and POCO: App info > Battery saver > **No restrictions**, and turn on **Autostart**.
- **In PPBrowser:** Settings > Media & playback > **Keep audio playing in background** on. It is on by default.

## Step 5: Reconnect

Unplug the phone and plug it back in, or force-stop Android Auto (App info > Force stop). With wireless Android Auto, turn the car's Bluetooth off and on. The car launcher refreshes and PPBrowser appears.

## Getting updates

- **Automatically:** Google Play installs new versions like any other app.
- **In the app:** Menu > Check for Updates > **Open in Google Play**.
- **By hand:** download the newest APK from the link in step 1 and open it.

Updates install over the old version and keep your bookmarks and settings.

## Using it in the car

- **Parked:** open PPBrowser from the car launcher for the full browser.
- **Driving:** Android Auto locks the browser screen while the car moves. Audio you started keeps playing, and PPBrowser in the media section lets you pick your quick links and bookmarks and control them with Android Auto's player or the steering wheel. See [Driving Safety](driving-safety.md).

## Troubleshooting

### PPBrowser doesn't appear in the car

Work down this list:

1. **Unknown sources got switched off.** Android Auto updates can reset developer settings. Repeat step 2.
2. **It's hidden in the launcher.** Repeat step 3.
3. **The car hasn't refreshed.** Reconnect as in step 5.
4. **Wrong profile.** If PPBrowser was installed in a cloned-app space, a second space or a work profile, install it in the main profile instead.
5. **Android Auto is out of date.** Update it from the Play Store, then reconnect.
6. **It shows up but won't open the first time.** Open another navigation app on the head unit first (for example Waze), then open PPBrowser.

### PPBrowser crashes

1. Update **Android System WebView** and **Chrome** from the Play Store. PPBrowser draws every page with WebView. If WebView is missing or in the middle of an update, PPBrowser now tells you instead of closing.
2. Update to the latest PPBrowser.
3. If it crashes as soon as it opens: App info > Storage > **Clear cache**. This keeps your bookmarks and settings.
4. Send a crash report so it can be fixed: **Settings > Android Auto and help > Share crash report**, then send the text to whoever looks after this build. The report contains the app version, the Android version, the phone model and the technical error. Nothing is sent unless you share it.

### Audio stops after a few minutes

Check the battery setting in step 4 and that **Keep audio playing in background** is on.

### A page picked in the car's media list stays silent

Some players only start after a tap on their own play button. For those, start playback while parked; it keeps playing when you drive off.

### "App not installed" when updating

The copy on the phone was signed with a different key, for example one you built yourself. Uninstall it first (this deletes its bookmarks), then install PPBrowser from Google Play.

## Reporting a problem

Collect your phone model, Android version, Android Auto version (at the bottom of Android Auto's settings), your car or head unit, and a crash report if there is one.

See also: [Driving Safety](driving-safety.md) · [Privacy Policy](privacy.md) · [Terms of Use](terms.md)
