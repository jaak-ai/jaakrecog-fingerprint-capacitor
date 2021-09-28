export interface FingerPrintPlugin {
   callFingerAcquisition(options:{accessToken: string,is_production:boolean}): Promise<{fingerLeft: string, fingerRigth: string}>;
}
