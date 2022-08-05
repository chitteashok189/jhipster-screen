import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PartyGroupService } from '../service/party-group.service';

import { PartyGroupComponent } from './party-group.component';

describe('PartyGroup Management Component', () => {
  let comp: PartyGroupComponent;
  let fixture: ComponentFixture<PartyGroupComponent>;
  let service: PartyGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PartyGroupComponent],
    })
      .overrideTemplate(PartyGroupComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyGroupComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PartyGroupService);

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
    expect(comp.partyGroups?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
