import { registerPlugin } from '@capacitor/core';

import type { FingerPrintPlugin } from './definitions';

const FingerPrint = registerPlugin<FingerPrintPlugin>('FingerPrint', {
  web: () => import('./web').then(m => new m.FingerPrintWeb()),
});

export * from './definitions';
export { FingerPrint };
