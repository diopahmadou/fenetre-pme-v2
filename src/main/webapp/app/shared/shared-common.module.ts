import { NgModule } from '@angular/core';

import { FenetrePmEv2SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [FenetrePmEv2SharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [FenetrePmEv2SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class FenetrePmEv2SharedCommonModule {}
