import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LightComponent } from './list/light.component';
import { LightDetailComponent } from './detail/light-detail.component';
import { LightUpdateComponent } from './update/light-update.component';
import { LightDeleteDialogComponent } from './delete/light-delete-dialog.component';
import { LightRoutingModule } from './route/light-routing.module';

@NgModule({
  imports: [SharedModule, LightRoutingModule],
  declarations: [LightComponent, LightDetailComponent, LightUpdateComponent, LightDeleteDialogComponent],
  entryComponents: [LightDeleteDialogComponent],
})
export class LightModule {}
