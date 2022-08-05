import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SecurityGroupComponent } from './list/security-group.component';
import { SecurityGroupDetailComponent } from './detail/security-group-detail.component';
import { SecurityGroupUpdateComponent } from './update/security-group-update.component';
import { SecurityGroupDeleteDialogComponent } from './delete/security-group-delete-dialog.component';
import { SecurityGroupRoutingModule } from './route/security-group-routing.module';

@NgModule({
  imports: [SharedModule, SecurityGroupRoutingModule],
  declarations: [SecurityGroupComponent, SecurityGroupDetailComponent, SecurityGroupUpdateComponent, SecurityGroupDeleteDialogComponent],
  entryComponents: [SecurityGroupDeleteDialogComponent],
})
export class SecurityGroupModule {}
