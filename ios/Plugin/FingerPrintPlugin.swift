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
    private var callbackId: String?

//    @objc func echo(_ call: CAPPluginCall) {
//        let value = call.getString("value") ?? ""
//        call.resolve([
//            "value": implementation.echo(value)
//        ])
//    }
    
    @objc func callFingerAcquisition(_ call: CAPPluginCall) {
        self.callbackId = call.callbackId
        self.bridge?.saveCall(call)
        
        let apiKey = call.getString("accessToken") ?? ""
        
        // TODO: Not sure why apiKey is empty here.
        print("Api Key: \(apiKey)")
        if let environment = call.getBool("is_production") {
            print("Environment: \(environment ? "production" : "development")")
        }
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
            onboardingVC.jwt = jwt
            onboardingVC.delegate = self
            bridge.viewController?.present(onboardingVC, animated: true, completion: nil)
        }
    }
}

extension FingerPrintPlugin: FAMainViewControllerDelegate {
    
	public func faMainViewControllerDidEndWithResult(results: [FAResult]) {
        	print("results: \(results)")
    	}

	public func faMainViewControllerDidEndWithResult(results: [FAHand : String]) {
        // Results has the wsq in each value, instead of a path to a .wsq file
        // TODO: Change this to have a local path instead of the actual .wsq
        
        guard let bridge = self.bridge else {
            return
        }
        
        DispatchQueue.main.async {
            bridge.dismissVC(animated: true) {
                print("------ WSQ ------ ")
                print(results)
                guard let callbackId = self.callbackId else {
                    return
                }
                
                let pluginCall = bridge.savedCall(withID: callbackId)
                
                // Added same keys and values set in the Android counterpart
                
                pluginCall?.resolve([
                    "fingerRigth" : results[.right(.index)] ?? "",
                    "fingerLeft" : results[.left(.index)] ?? ""
                ])
            }
        }
    }
}
