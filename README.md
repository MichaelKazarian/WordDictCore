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

To include `WordDictCore` in your Android or Java project, add the following dependency to your module's `build.gradle` file:

```gradle
dependencies {
    implementation project(':worddictcore')
    // OR if published to Maven:
    // implementation 'com.github.MichaelKazarian:word-dict-core:1.0.0-gpl'
}
