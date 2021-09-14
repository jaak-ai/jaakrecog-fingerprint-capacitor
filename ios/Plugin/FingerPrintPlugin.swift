import Foundation
import Capacitor
import JAAKFingerAcquisition
/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FingerPrintPlugin)
public class FingerPrintPlugin: CAPPlugin {
    private let implementation = FingerPrint()
    private let authService = AuthServiceImplementation()

//    @objc func echo(_ call: CAPPluginCall) {
//        let value = call.getString("value") ?? ""
//        call.resolve([
//            "value": implementation.echo(value)
//        ])
//    }
    
    @objc func callFingerAcequisition(_ call: CAPPluginCall) {
        let apiKey = call.getString("value") ?? ""
        
        // TODO: Not sure why apiKey is empty here.
        print("Api Key: \(apiKey)")
        
        authService.validateCredentials(apiKey: apiKey) { result in
            switch result {
            case .failure(_):
                print("Error validating api key")
            case .success(let response):
                self.presentFingerOnboarding(jwt: response.jwt)
            }
        }
    }
    
    private func presentFingerOnboarding(jwt: String) {
        print("JWT: \(jwt)")
        
        guard let bridge = self.bridge else {
            return
        }
        
        DispatchQueue.main.async {
//            let bundle = Bundle(for: FAMainViewController.self)
            let onboardingVC = FAMainViewController()//.loadFromNib(bundle: bundle)
            bridge.viewController?.present(onboardingVC, animated: true, completion: nil)
        }
    }
}
