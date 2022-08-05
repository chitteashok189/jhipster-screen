import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartyNote, PartyNote } from '../party-note.model';
import { PartyNoteService } from '../service/party-note.service';

@Injectable({ providedIn: 'root' })
export class PartyNoteRoutingResolveService implements Resolve<IPartyNote> {
  constructor(protected service: PartyNoteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyNote> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((partyNote: HttpResponse<PartyNote>) => {
          if (partyNote.body) {
            return of(partyNote.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyNote());
  }
}
