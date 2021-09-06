export interface FingerPrintPlugin {
  callFingerAcequisition(accessToken: string): Promise<{ value: string }>;
}
