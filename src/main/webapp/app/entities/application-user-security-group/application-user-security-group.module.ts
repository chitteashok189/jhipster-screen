import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ApplicationUserSecurityGroupComponent } from './list/application-user-security-group.component';
import { ApplicationUserSecurityGroupDetailComponent } from './detail/application-user-security-group-detail.component';
import { ApplicationUserSecurityGroupUpdateComponent } from './update/application-user-security-group-update.component';
import { ApplicationUserSecurityGroupDeleteDialogComponent } from './delete/application-user-security-group-delete-dialog.component';
import { ApplicationUserSecurityGroupRoutingModule } from './route/application-user-security-group-routing.module';

@NgModule({
  imports: [SharedModule, ApplicationUserSecurityGroupRoutingModule],
  declarations: [
    ApplicationUserSecurityGroupComponent,
    ApplicationUserSecurityGroupDetailComponent,
    ApplicationUserSecurityGroupUpdateComponent,
    ApplicationUserSecurityGroupDeleteDialogComponent,
  ],
  entryComponents: [ApplicationUserSecurityGroupDeleteDialogComponent],
})
export class ApplicationUserSecurityGroupModule {}
