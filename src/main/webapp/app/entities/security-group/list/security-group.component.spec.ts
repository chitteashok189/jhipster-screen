import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SecurityGroupService } from '../service/security-group.service';

import { SecurityGroupComponent } from './security-group.component';

describe('SecurityGroup Management Component', () => {
  let comp: SecurityGroupComponent;
  let fixture: ComponentFixture<SecurityGroupComponent>;
  let service: SecurityGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SecurityGroupComponent],
    })
      .overrideTemplate(SecurityGroupComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SecurityGroupComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SecurityGroupService);

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
    expect(comp.securityGroups?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
