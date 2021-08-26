export interface FingerPrintPlugin {
  callFingerAcequisition(options: { value: string }): Promise<{ value: string }>;
}
