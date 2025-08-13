import Foundation

// This is a dummy implementation for bridging integration with FoundationModels framework.
@objc public class SystemLanguageModelLLMClient: NSObject {

    @objc public func isSupported() -> Bool {
        return true
    }

    @objc public class func call(
        systemPrompt: String,
        userPrompt: String
    ) async throws -> String {
        return "Hello from Swift LLM Call"
    }

    @objc public class func callStreaming(
        systemPrompt: String,
        userPrompt: String,
        callback: @escaping (String?, NSError?) -> Void
    ) {
        Task {
            do {
                for await item in createAsyncSequence() {
                    DispatchQueue.main.async {
                        callback(item, nil)
                    }
                }
                DispatchQueue.main.async {
                    callback(nil, nil)
                } // completion signal
            } catch {
                let nsError = error as NSError
                DispatchQueue.main.async {
                    callback(nil, nsError)
                }
            }
        }
    }
}

private func createAsyncSequence() -> AsyncStream<String> {
    AsyncStream { continuation in
        let task = Task {
            for i in 1...5 {
                continuation.yield("Token-\(i)")
                try await Task.sleep(nanoseconds: 100_000_000)
            }
            continuation.finish()
        }
        continuation.onTermination = { _ in
            task.cancel()
        }
    }
}

