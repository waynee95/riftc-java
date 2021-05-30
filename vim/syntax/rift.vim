" Vim syntax file
" Language: Rift
" Filenames: *.rift
" Maintainer: waynee95

if exists("b:current_syntax")
  finish
endif

syn keyword riftKeywords break let in end nil fn var val type extern match with
syn keyword riftRepeat while do
syn keyword riftConditional if then else
syn keyword riftBool true false nil
syn keyword riftType i64 string bool

syn keyword riftOperator new break continue

syn match riftNumber "^\d\+"

syn region riftString start='"' end='"'
syn region riftCommentLine start="//" end="$"

" Taken from https://github.com/playXE/vim-dora/blob/master/syntax/dora.vim
syn match riftFunction "\zs\(\k\w*\)*\s*\ze("

hi def link riftKeywords Keyword
hi def link riftRepeat Repeat
hi def link riftConditional Conditional
hi def link riftBool Boolean
hi def link riftType Type
hi def link riftNumber Number
hi def link riftString String
hi def link riftCommentLine Comment
hi def link riftOperator Operator
hi def link riftFunction Function

let b:current_syntax = "rift"
