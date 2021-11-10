export interface FingerPrintPlugin {
   callFingerAcquisition(options:{accessToken: string,is_production:boolean}): Promise<{eventIdLeft: string,acquireLeft: boolean,eventIdRight: string,acquireRight: boolean}>;
}
