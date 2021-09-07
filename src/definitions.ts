export interface FingerPrintPlugin {
  callFingerAcequisition(options: { apiKey: string,develop:boolean }): Promise<{ value: string }>;
}
