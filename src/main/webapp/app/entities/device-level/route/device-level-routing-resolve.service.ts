import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDeviceLevel, DeviceLevel } from '../device-level.model';
import { DeviceLevelService } from '../service/device-level.service';

@Injectable({ providedIn: 'root' })
export class DeviceLevelRoutingResolveService implements Resolve<IDeviceLevel> {
  constructor(protected service: DeviceLevelService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeviceLevel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((deviceLevel: HttpResponse<DeviceLevel>) => {
          if (deviceLevel.body) {
            return of(deviceLevel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DeviceLevel());
  }
}
