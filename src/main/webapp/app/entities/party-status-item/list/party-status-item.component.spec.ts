import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PartyStatusItemService } from '../service/party-status-item.service';

import { PartyStatusItemComponent } from './party-status-item.component';

describe('PartyStatusItem Management Component', () => {
  let comp: PartyStatusItemComponent;
  let fixture: ComponentFixture<PartyStatusItemComponent>;
  let service: PartyStatusItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PartyStatusItemComponent],
    })
      .overrideTemplate(PartyStatusItemComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyStatusItemComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PartyStatusItemService);

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
    expect(comp.partyStatusItems?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
