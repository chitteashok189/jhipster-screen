import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InspectionRecordComponent } from '../list/inspection-record.component';
import { InspectionRecordDetailComponent } from '../detail/inspection-record-detail.component';
import { InspectionRecordUpdateComponent } from '../update/inspection-record-update.component';
import { InspectionRecordRoutingResolveService } from './inspection-record-routing-resolve.service';

const inspectionRecordRoute: Routes = [
  {
    path: '',
    component: InspectionRecordComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InspectionRecordDetailComponent,
    resolve: {
      inspectionRecord: InspectionRecordRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InspectionRecordUpdateComponent,
    resolve: {
      inspectionRecord: InspectionRecordRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InspectionRecordUpdateComponent,
    resolve: {
      inspectionRecord: InspectionRecordRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(inspectionRecordRoute)],
  exports: [RouterModule],
})
export class InspectionRecordRoutingModule {}
