import { WebPlugin } from '@capacitor/core';

import type { FingerPrintPlugin } from './definitions';

export class FingerPrintWeb extends WebPlugin implements FingerPrintPlugin {
    async callFingerAcquisition(options:{accessToken: string,is_production:boolean}): Promise<{eventIdLeft: string,acquireLeft: boolean,eventIdRight: string,acquireRight: boolean}> {
      console.log('ECHO', options);
      return {eventIdLeft: "",acquireLeft: false,eventIdRight: "",acquireRight: false};
    }

}
