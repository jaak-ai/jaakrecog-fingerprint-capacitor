import { WebPlugin } from '@capacitor/core';

import type { FingerPrintPlugin } from './definitions';

export class FingerPrintWeb extends WebPlugin implements FingerPrintPlugin {
  async callFingerAcequisition(acccessToken: string): Promise<{ value: string }> {
    console.log('ECHO', acccessToken);
    return {value: 'This is a test'};
  }
}
