<div align="center">
    <img alt="Icon" src="app/android/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png" width="200" />
</div>

<h2 align="center">
    Grodno Roads - actual information on the road
</h2>

<p align="center">
    <a target="_blank" href="https://github.com/egorikftp/GrodnoRoads/stargazers"><img src="https://img.shields.io/github/stars/egorikftp/GrodnoRoads.svg"></a>
    <a href="https://github.com/egorikftp/GrodnoRoads/network"><img alt="API" src="https://img.shields.io/github/forks/egorikftp/GrodnoRoads.svg"/></a>
    <a target="_blank" href="https://github.com/egorikftp/GrodnoRoads/blob/release/LICENSE"><img src="https://img.shields.io/github/license/egorikftp/GrodnoRoads.svg"></a>
    <a target="_blank" href="https://t.me/grodno_roads"><img src="https://img.shields.io/badge/Telegram%20Channel-blue"></a>
</p>

![Download Status](https://playbadges.pavi2410.me/badge/full?id=com.egoriku.grodnoroads)

# EN
The application shows on the map the current situation on the roads of Grodno and the region.

Current functionality:
- display of stationary/temporary/mobile cameras
- the ability to send events about the traffic police and accidents
- voice alerts


# Try without building sources
<a href='https://play.google.com/store/apps/details?id=com.egoriku.grodnoroads'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width="200"/></a>

## Issues
In case bug and suggestions, please create issue.

### Useful gradle commands

Check dependency updates: `./gradlew dependencyUpdates`
Run compose metrics: `./gradlew app:android:assembleRelease -PenableComposeCompilerReports=true` 
Generate kmp buildconfig: `./gradlew :kmp:shared:components:generateBuildKonfig`

Build ui-demo: `./gradlew app:ui-demo:assembleRelease`

Build app debug: `./gradlew app:android:assembleDebug`
Build app release: `./gradlew app:android:assembleRelease`