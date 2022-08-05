import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PartyStatusService } from '../service/party-status.service';

import { PartyStatusComponent } from './party-status.component';

describe('PartyStatus Management Component', () => {
  let comp: PartyStatusComponent;
  let fixture: ComponentFixture<PartyStatusComponent>;
  let service: PartyStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PartyStatusComponent],
    })
      .overrideTemplate(PartyStatusComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyStatusComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PartyStatusService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.partyStatuses?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
