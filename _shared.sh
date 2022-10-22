function download {
  local url=$1
  local outDir=$2
  local outFile=$3

  if [[ -d "${outDir}" ]]; then
    echo "# folder ${outDir} already exists"
  else
    mkdir -p "${outDir}" || exit 98
    echo "# created folder: ${outDir}"
  fi

  local fullDir="${outDir}/${outFile}"
  if [[ -f "${fullDir}" ]]; then
    echo "# file ${fullDir} already exists"
  else
    echo "# downloading ${url} into ${fullDir}"
    wget "${url}" -O "${fullDir}"
  fi
}
