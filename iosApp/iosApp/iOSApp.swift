import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        KoinComminKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
                .ignoresSafeArea()
        }
    }
}
