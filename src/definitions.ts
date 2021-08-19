export interface FingerPrintPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
