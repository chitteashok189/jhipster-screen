import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ScoutingComponent } from './list/scouting.component';
import { ScoutingDetailComponent } from './detail/scouting-detail.component';
import { ScoutingUpdateComponent } from './update/scouting-update.component';
import { ScoutingDeleteDialogComponent } from './delete/scouting-delete-dialog.component';
import { ScoutingRoutingModule } from './route/scouting-routing.module';

@NgModule({
  imports: [SharedModule, ScoutingRoutingModule],
  declarations: [ScoutingComponent, ScoutingDetailComponent, ScoutingUpdateComponent, ScoutingDeleteDialogComponent],
  entryComponents: [ScoutingDeleteDialogComponent],
})
export class ScoutingModule {}
