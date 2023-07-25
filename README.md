# Twitch4j onboarding project

The purpose of this project is to ease onboarding of new members to Gradle Enterprise. We've set this up to have all of 
the commonly encountered build caching issues when helping customers improve their builds. The full list of caching issues
this project has is written below.

There are two branches used for the onboarding: `master` and `onboarding-broken-build`. `master` has most of the problems,
while `onboarding-broken-build` has some issues that actually break the build, so that's the reason for it being in a 
separate branch.

**Do not push to this repository**, instead if you want to push your commits create a fork and do it there - 
you will need to fork if you want to tackle the CI part of experiments when onboarding.

Your starting point is the `master` branch. Good luck! :)

## List of issues 

* Absolute paths
* Missing inputs specification
* Missing outputs specification
* Task not producing an output file(s)
* Caching not enabled for task(s)
* Configuration time user triggered dependency resolution
* Dependency resolution conflict (separate branch / broken build)
* Dependency resolution version upgrade resulting in a broken build (separate branch)
* Volatile lines in `MANIFEST.MF` (timestamp, username, hostname, OS)
* Overlapping outputs
* Missing declared task dependencies
* Build dependency resolution conflict (separate branch)
* Runtime dependency resolution causing issues (separate branch)

## Disclaimers

* Forked from the [twitch4j project](https://github.com/twitch4j/twitch4j) and modified for our needs
* [Original README](README-ORIGINAL.md)
* [MIT License](LICENSE)

## Branches

* [master](https://github.com/gradle/solutions-gradle-build-onboarding) - ([changes](https://github.com/gradle/solutions-gradle-build-onboarding/compare/OnboardingBase...gradle:solutions-gradle-build-onboarding:master)), which is set up with all the problems:
  * Absolute paths
  * Missing inputs specification
  * Missing outputs specification
  * Task not producing an output file(s)
  * Caching not enabled for task(s)
  * Configuration time user triggered dependency resolution
  * Volatile lines in MANIFEST.MF (timestamp, username, hostname, OS)
  * Overlapping outputs
  * Missing declared task dependencies
* [onboarding-broken-build](https://github.com/gradle/solutions-gradle-build-onboarding/tree/onboarding-broken-build) - ([changes](https://github.com/gradle/solutions-gradle-build-onboarding/compare/master...gradle:solutions-gradle-build-onboarding:onboarding-broken-build)), which breaks the build in two ways:
  * Dependency resolution conflict for build dependencies
  * Dependency resolution version upgrade resulting in a broken build
* [gk/onboarding-setup-fixes](https://github.com/gradle/solutions-gradle-build-onboarding/tree/gk/onboarding-setup-fixes) - ([changes](https://github.com/gradle/solutions-gradle-build-onboarding/compare/master...gradle:solutions-gradle-build-onboarding:gk/onboarding-setup-fixes)), which fixes all of the above problems.
* [gk/onboarding-ge-solutions](https://github.com/gradle/solutions-gradle-build-onboarding/tree/gk/onboarding-ge-solutions) - ([changes](https://github.com/gradle/solutions-gradle-build-onboarding/compare/master...gradle:solutions-gradle-build-onboarding:gk/onboarding-ge-solutions)), follows the `master` branch but has a basic GE setup for the [Solutions GE instance](https://ge.solutions-team.gradle.com/scans)
 
## Contributing

When adding/modifying this project to include new problems, you should:

- Update his readme by specifying what problem was added in the `List of issues`, as well as in the `Branches` part specifying
on which branch it was added or if a new branch was added and what was done there.
- After your changes are merged to `master`, you should merge `master` into [gk/onboarding-ge-solutions](https://github.com/gradle/solutions-gradle-build-onboarding/tree/gk/onboarding-ge-solutions) and
  [gk/onboarding-setup-fixes](https://github.com/gradle/solutions-gradle-build-onboarding/tree/gk/onboarding-setup-fixes)
- You should implement a fix of the new issue on the branch [gk/onboarding-setup-fixes](https://github.com/gradle/solutions-gradle-build-onboarding/tree/gk/onboarding-setup-fixes).
