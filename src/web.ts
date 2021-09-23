import { WebPlugin } from '@capacitor/core';

import type { FingerPrintPlugin } from './definitions';

export class FingerPrintWeb extends WebPlugin implements FingerPrintPlugin {
    async callFingerAcquisition(options:{accessToken: string,is_production:boolean}): Promise<{fingerLeft: string, fingerRigth: string}> {
      console.log('ECHO', options);
           // console.log('ECHO', develop);

      return {fingerLeft: "",fingerRigth:""};
    }

}
