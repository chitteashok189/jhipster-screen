import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DiseaseControlComponent } from '../list/disease-control.component';
import { DiseaseControlDetailComponent } from '../detail/disease-control-detail.component';
import { DiseaseControlUpdateComponent } from '../update/disease-control-update.component';
import { DiseaseControlRoutingResolveService } from './disease-control-routing-resolve.service';

const diseaseControlRoute: Routes = [
  {
    path: '',
    component: DiseaseControlComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DiseaseControlDetailComponent,
    resolve: {
      diseaseControl: DiseaseControlRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DiseaseControlUpdateComponent,
    resolve: {
      diseaseControl: DiseaseControlRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DiseaseControlUpdateComponent,
    resolve: {
      diseaseControl: DiseaseControlRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(diseaseControlRoute)],
  exports: [RouterModule],
})
export class DiseaseControlRoutingModule {}
