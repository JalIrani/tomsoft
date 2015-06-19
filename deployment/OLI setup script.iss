

[Setup]
AppName=Object Lab Interface
AppVersion=1.1
DefaultDirName={pf}\Towson Univeristy
DefaultGroupName=OLI
UninstallDisplayIcon={app}\uninstallOLI.exe
Compression=lzma2
SolidCompression=yes
OutputDir=userdocs:Inno Setup Examples Output

[Files]
Source: "OLI.exe"; DestDir: "{app}";
Source: "app\*"; DestDir: "{app}\app"; Flags: ignoreversion recursesubdirs;
Source: "runtime\*"; DestDir: "{app}\runtime"; Flags: ignoreversion recursesubdirs;
Source: "mysql-installer-web-community-5.6.23.0.msi"; DestDir: {tmp}; Flags: deleteafterinstall;
Source: "googledrivesync.exe"; DestDir: {tmp}; Flags: deleteafterinstall;
Source: "OLI_Icon.ico"; DestDir: "{app}";
Source: "setup_jobsdb_script.sql"; Flags: dontcopy;
       
[Icons]
Name: "{group}\Object Lab Interface"; IconFilename: {app}\Oli_Icon.ico; Filename: "{app}\OLI.EXE"; WorkingDir: "{app}"; Comment: "Object Lab Interface";

[Run]
Filename: "{tmp}\googledrivesync.exe";

[Dirs]
Name: "C:\Sync"
Name: "C:\Sync\Export"
Name: "C:\Sync\ObjectLabPrinters"

[Code]
var
  MyProgChecked: Boolean;
  MyProgCheckResult: Boolean;
  FinishedInstall: Boolean;


procedure CurStepChanged(CurStep: TSetupStep);
begin
  Log('CurStepChanged(' + IntToStr(Ord(CurStep)) + ') called');
  if CurStep = ssPostInstall then
    FinishedInstall := True;
end;

procedure CurInstallProgressChanged(CurProgress, MaxProgress: Integer);
begin
  Log('CurInstallProgressChanged(' + IntToStr(CurProgress) + ', ' + IntToStr(MaxProgress) + ') called');
end;

function NextButtonClick(CurPageID: Integer): Boolean;
var
  ResultCode: Integer;
var
  ErrorCode: Integer;
var
  S: AnsiString;
begin
  Log('NextButtonClick(' + IntToStr(CurPageID) + ') called');
  case CurPageID of
    wpSelectDir: ;
      //MsgBox('NextButtonClick:' #13#13 'You selected: ''' + WizardDirValue + '''.', mbInformation, MB_OK);
      //ForceDirectories('C:\Sync') : FolderDir;
    wpSelectProgramGroup:  ;
      //MsgBox('NextButtonClick:' #13#13 'You selected: ''' + WizardGroupValue + '''.', mbInformation, MB_OK);
    wpReady:
      begin
        if MsgBox('Would you like to install the database on this machine?' #13#13, mbConfirmation, MB_YESNO) = idYes then begin
                    ExtractTemporaryFile('mysql-installer-web-community-5.6.23.0.msi');              
                                  ShellExec('', 'msiexec',
  ExpandConstant('/I "{tmp}\mysql-installer-web-community-5.6.23.0.msi" /qb'),
  '', SW_SHOWNORMAL, ewWaitUntilTerminated, ErrorCode);
        
        ExtractTemporaryFile('setup_jobsdb_script.sql');
        Result := True;
        ExtractTemporaryFile('setup_jobsdb_script.sql');
        if FileCopy(ExpandConstant('{tmp}\setup_jobsdb_script.sql'), 
          ExpandConstant('{app}\setup_jobsdb_script.sql'), False) 
        then;
          //MsgBox('Setup SQL copying succeeded!', mbInformation, MB_OK)
        else;
          //MsgBox('File copying failed!', mbError, MB_OK)

        BringToFrontAndRestore();
        //MsgBox('NextButtonClick:' #13#13 'The normal installation will now start.', mbInformation, MB_OK);
            end;
      end;
  end;

  Result := True;
end;

function BackButtonClick(CurPageID: Integer): Boolean;
begin
  Log('BackButtonClick(' + IntToStr(CurPageID) + ') called');
  Result := True;
end;

function ShouldSkipPage(PageID: Integer): Boolean;
begin
  Log('ShouldSkipPage(' + IntToStr(PageID) + ') called');
  { Skip wpInfoBefore page; show all others }
  case PageID of
    wpInfoBefore:
      Result := True;
  else
    Result := False;
  end;
end;

function MyProgCheck(): Boolean;
begin
  Log('MyProgCheck() called');
  if not MyProgChecked then begin
    //MyProgCheckResult := MsgBox('MyProgCheck:' #13#13 'Using the script you can decide at runtime to include or exclude files from the installation. Do you want to install MyProg.exe and MyProg.chm to ' + ExtractFilePath(CurrentFileName) + '?', mbConfirmation, MB_YESNO) = idYes;
    //MyProgChecked := True;
  end;
  Result := MyProgCheckResult;
end;


function MyConst(Param: String): String;
begin
  Log('MyConst(''' + Param + ''') called');
  Result := ExpandConstant('{pf}');
end;