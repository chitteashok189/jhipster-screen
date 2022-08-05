import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DiseaseComponent } from '../list/disease.component';
import { DiseaseDetailComponent } from '../detail/disease-detail.component';
import { DiseaseUpdateComponent } from '../update/disease-update.component';
import { DiseaseRoutingResolveService } from './disease-routing-resolve.service';

const diseaseRoute: Routes = [
  {
    path: '',
    component: DiseaseComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DiseaseDetailComponent,
    resolve: {
      disease: DiseaseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DiseaseUpdateComponent,
    resolve: {
      disease: DiseaseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DiseaseUpdateComponent,
    resolve: {
      disease: DiseaseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(diseaseRoute)],
  exports: [RouterModule],
})
export class DiseaseRoutingModule {}
