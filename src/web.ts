import { WebPlugin } from '@capacitor/core';

import type { FingerPrintPlugin } from './definitions';

export class FingerPrintWeb extends WebPlugin implements FingerPrintPlugin {
  async callFingerAcequisition(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
