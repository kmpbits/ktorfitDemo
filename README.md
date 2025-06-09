# ktorfitDemo

A minimalist Kotlin Multiplatform (KMP) demo showcasing how to use **Ktorfit** a Retrofit-style library for API calls in a Compose Multiplatform project.

> ⚙️ Part of the [KMP Bits](https://github.com/kmpbits) series — hands-on examples for real-world KMP development.

---

## ✨ Features

- **Koin Annotations**: Use annotations to make a request in an interface, like Retrofit
- **Compose Multiplatform UI**: Shared UI code across Android and iOS platforms.
- **Gradle & KSP Configuration**: Set up KSP for code generation and include generated sources in your build.
- **ViewModel Integration**: Manage state with `StateFlow` and handle asynchronous operations using `viewModelScope`.

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio**: Giraffe or newer.
- **Kotlin**: 1.9 or newer.
- **KSP Plugin**: Ensure KSP is applied and configured correctly.

### Running the App

1. **Clone the repository**:

    ```bash
    git clone https://github.com/kmpbits/ktorfitDemo.git
    cd ktorfitDemo
    ```

2. **Open in Android Studio**  
   Open the project in Android Studio and let it sync.

3. **Run on Android**  
   Select the `androidApp` configuration and click **Run**.

4. **Run on iOS**  
   Open the `iosApp` module in Xcode, choose a simulator or device, and click **Run**.

---

## 🧩 Project Structure

```
KoinInjectDemo/
├── androidApp/         # Android-specific code
├── iosApp/             # iOS-specific code
├── composeApp/         # Shared KMP logic and Compose UI
├── build.gradle.kts    # Root build file
├── settings.gradle.kts # Gradle project settings
└── README.md
```

---

## 💡 Concepts Covered

- Using `@GET`, `@POST`, `@PUT` and `@DELETE` for API calls
- Gradle configuration for KSP and metadata generation
- Get a list of todos and then update, add a new one or delete
---

## 📚 Learn More

- 📖 [Using Ktorfit in KMP](https://your-article-link.com)
- 🧰 [Koin Official Documentation](https://insert-koin.io/docs)
- 🧪 [Koin Testing Guide](https://insert-koin.io/docs/reference/koin-test/)
- 💡 [KMP Bits Repository](https://github.com/kmpbits)
