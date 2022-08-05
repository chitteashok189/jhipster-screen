import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PartyRoleService } from '../service/party-role.service';

import { PartyRoleComponent } from './party-role.component';

describe('PartyRole Management Component', () => {
  let comp: PartyRoleComponent;
  let fixture: ComponentFixture<PartyRoleComponent>;
  let service: PartyRoleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PartyRoleComponent],
    })
      .overrideTemplate(PartyRoleComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyRoleComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PartyRoleService);

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
    expect(comp.partyRoles?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
