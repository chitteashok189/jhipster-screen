import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DeviceLevelComponent } from './list/device-level.component';
import { DeviceLevelDetailComponent } from './detail/device-level-detail.component';
import { DeviceLevelUpdateComponent } from './update/device-level-update.component';
import { DeviceLevelDeleteDialogComponent } from './delete/device-level-delete-dialog.component';
import { DeviceLevelRoutingModule } from './route/device-level-routing.module';

@NgModule({
  imports: [SharedModule, DeviceLevelRoutingModule],
  declarations: [DeviceLevelComponent, DeviceLevelDetailComponent, DeviceLevelUpdateComponent, DeviceLevelDeleteDialogComponent],
  entryComponents: [DeviceLevelDeleteDialogComponent],
})
export class DeviceLevelModule {}
