import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ApplicationUserSecurityGroupService } from '../service/application-user-security-group.service';

import { ApplicationUserSecurityGroupComponent } from './application-user-security-group.component';

describe('ApplicationUserSecurityGroup Management Component', () => {
  let comp: ApplicationUserSecurityGroupComponent;
  let fixture: ComponentFixture<ApplicationUserSecurityGroupComponent>;
  let service: ApplicationUserSecurityGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ApplicationUserSecurityGroupComponent],
    })
      .overrideTemplate(ApplicationUserSecurityGroupComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApplicationUserSecurityGroupComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ApplicationUserSecurityGroupService);

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
    expect(comp.applicationUserSecurityGroups?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
