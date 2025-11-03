# WordDictCore

**Description:** A reusable Java library providing core dictionary data models and specialized bot functionality for extracting structured linguistic information from Wiktionary.

---

## Overview

**WordDictCore** is the fundamental, reusable Java library that provides the core data infrastructure and specialized tools for the **WordDolphin** project.

It is designed as a **decoupled Java Library backend**, ensuring that all data processing and acquisition logic is strictly separate from the platform-specific Android implementation.

---

## Key Functionality

The module encapsulates two main areas of functionality, corresponding to its primary internal packages:

### 1. Core Dictionary Logic (`com.worddict.worddictcore`)

This package defines the essential data structures and utility methods for handling dictionary information.

* **Word Management:** Defines core data models for dictionary entries (words, definitions, examples).
* **Data Handling:** Provides mechanisms for the serialization and deserialization of word data (including handling external dependencies like JSON parsing).
* **Utility Operations:** Offers general utilities necessary for text and linguistic processing within the application.

### 2. Wiktionary Data Extraction (`com.worddict.wiktionarybot`)

This package contains the specialized tools for interacting with the Wiktionary knowledge base.

* **Bot Functionality:** Contains the necessary logic for connecting to and efficiently **extracting structured linguistic data** from the **Wiktionary** project.
* **Integration:** Utilizes the core data models provided by `com.worddict.worddictcore` to ensure that extracted information is immediately usable within the main `WordDolphin` application.

---

## Installation (Gradle)

To include `WordDictCore` in your Android or Java project, add one of the following options to your module's `build.gradle` file:

### 1. Local Project Module
If you keep `worddictcore` as a local module:
```gradle
dependencies {
    implementation project(':worddictcore')
}
```

### 2. Published via JitPack
If you use the GitHub repository published through [JitPack](https://jitpack.io/)
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    // For latest development version (SNAPSHOT)
    implementation 'com.github.MichaelKazarian:WordDictCore:master-SNAPSHOT'

    // For stable release versions (recommended for production)
    // implementation 'com.github.MichaelKazarian:WordDictCore:1.0.0'
}
```

> ** ⚠️ Note:**
>
> master-SNAPSHOT allows you to automatically get the latest changes from the master branch. To refresh cached dependencies, run:
> ```sh
> ./gradlew build --refresh-dependencies
> ```
> Stable release versions (tags) are recommended for production, as they provide a fixed, unchanging artifact.

---

## Quick Start

Here is a minimal example showing how to use the library:

```
import com.worddict.worddictcore.Word;
import com.worddict.worddictcore.Translation;
import com.worddict.wiktionarybot.Wiktionary;
import com.worddict.wiktionarybot.WiktionaryEnglish;

public class Example {
    public static void main(String[] args) {
        // Create a Wiktionary instance
        Wiktionary wiktionary = WiktionaryEnglish.newInstance();

        // Search for a word
        wiktionary.search("test");

        // Get translations to German and Ukrainian
        String[] langCodes = {"fr", "uk"};
        Translation[] translations = wiktionary.getTranslation(langCodes);

        for (Translation t : translations) {
            System.out.println(t.getText());
        }

        // Get IPA pronunciations
        System.out.println("IPA:");
        for (var p : wiktionary.getIPA()) {
            System.out.println(p.getText());
        }

        // Get audio samples
        System.out.println("Audio Samples:");
        for (var a : wiktionary.getAudioSamples()) {
            System.out.println(a.getUrl());
        }
    }
}
```

---

### Using the Makefile

The repository includes a Makefile for easier build and test management.

Basic commands:
```sh
# Compile the project using Maven
make build

# Run unit tests
make test

# Clean the build artifacts
make clean
```

> ** ⚠️ Note:**
> The Makefile is just a convenient wrapper over Maven commands. You can still use `mvn clean install` directly if preferred.
