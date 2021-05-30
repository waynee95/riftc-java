#!/usr/bin/env python3

import argparse
import os
import glob
import subprocess
import sys

def print_red(str):
  print("\033[91m" + str + "\033[0m")

def print_green(str):
  print("\033[92m" + str + "\033[0m")

parser = argparse.ArgumentParser()
parser.add_argument("--srcdir", default=".", help="specify the source folder of the Rift compiler")
parser.add_argument("--testdir", default="test", help="specify the folder for all test cases (default: src_dir/test")
parser.add_argument("--verbose", default=False, help="print every result from every test file")
parser.add_argument("--phase", nargs="+", choices=("lex", "parse", "typecheck"), help="specify which test cases to run. default=all")
args = parser.parse_args()

src_dir = args.srcdir
test_dir = os.path.join(src_dir, args.testdir)
verbose = args.verbose
test_cases = args.phase

if test_cases == None:
  test_cases = ["lex", "parse", "typecheck"]

print(os.path.join(src_dir, "Makefile"))

if not os.path.isfile(os.path.join(src_dir, "Makefile")):
  print("Error: Cannot find Makefile!")
  sys.exit(1)
if not os.path.exists(test_dir):
  print(f"Error: folder {test_dir} does not exist!")
  sys.exit(1)

def runTestCases(dir, flag):
  print(f"Running test cases for {dir}...")
  files = glob.glob(f"{dir}/*.rift");
  total = len(files)
  failed = 0
  for file in files:
    command = ["./riftc", flag, file]
    result = subprocess.run(command, capture_output=True, cwd=src_dir)
    if "accept" in dir:
      if not "Accepted." in str(result.stdout):
        failed = failed + 1
        if verbose:
          print_red(f" {file} failed!")
      else:
        if verbose:
          print_green(f" {file} was successful!")
    else:
      if not "Rejected." in str(result.stderr):
        failed = failed + 1
        if verbose:
          print_red(f" {file} failed!")
      else:
        if verbose:
          print_green(f" {file} was successful!")
  print(f"\n{failed} tests failed!\n")

print("Building riftc...\n")
subprocess.run(["make" , "clean"], cwd=src_dir)
subprocess.run(["make"], cwd=src_dir)

print("\nStarting test cases...\n======================\n")

for test in test_cases:
  accept = os.path.join(test_dir, test, "accept")
  reject = os.path.join(test_dir, test, "reject")
  if os.path.isdir(accept):
    runTestCases(accept, "--" + test)
  else:
    print(f"Error: {accept} is not a valid directory!")
    sys.exit(1)
  if os.path.isdir(reject):
    runTestCases(reject, "--" + test)
  else:
    print(f"Error: {reject} is not a valid directory!")
    sys.exit(1)
